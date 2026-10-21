package com.mathup.submission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mathup.exam.Exam;
import com.mathup.exam.ExamRepository;
import com.mathup.question.Question;
import com.mathup.question.QuestionType;
import com.mathup.submission.dto.SubmissionRequest;
import com.mathup.submission.dto.SubmissionResponse;
import com.mathup.user.UserRepository;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubmissionServiceTest {

    private static final long EXAM_ID = 1L;
    private static final long USER_ID = 7L;

    @Mock SubmissionRepository submissionRepository;
    @Mock ExamRepository examRepository;
    @Mock UserRepository userRepository;
    @Mock ExamAttemptRepository attemptRepository;
    @InjectMocks SubmissionService service;

    private Exam exam;

    @BeforeEach
    void setUp() {
        Question mc = Question.builder().id(10L).questionType(QuestionType.MULTIPLE_CHOICE).correctAnswer("A").build();
        Question tf = Question.builder().id(11L).questionType(QuestionType.TRUE_FALSE).correctAnswer("ĐSĐS").build();
        exam = Exam.builder().id(EXAM_ID).durationMinutes(90).questions(List.of(mc, tf)).build();
        when(examRepository.findById(EXAM_ID)).thenReturn(Optional.of(exam));
    }

    private void givenAttemptStartedMinutesAgo(long minutes) {
        ExamAttempt attempt = ExamAttempt.builder()
                .exam(exam)
                .startedAt(Instant.now().minusSeconds(minutes * 60))
                .build();
        when(attemptRepository.findFirstByExamIdAndUserIdAndSubmittedFalseOrderByStartedAtDesc(EXAM_ID, USER_ID))
                .thenReturn(Optional.of(attempt));
    }

    private static SubmissionRequest answers(String mc, String tf) {
        SubmissionRequest request = new SubmissionRequest();
        request.setAnswers(Map.of(10L, mc, 11L, tf));
        return request;
    }

    @Test
    void submit_withoutStartingIsRejected() {
        when(attemptRepository.findFirstByExamIdAndUserIdAndSubmittedFalseOrderByStartedAtDesc(EXAM_ID, USER_ID))
                .thenReturn(Optional.empty());
        assertThrows(ExamNotStartedException.class, () -> service.submit(EXAM_ID, USER_ID, answers("A", "ĐSĐS")));
    }

    @Test
    void submit_afterDeadlineIsRejected() {
        givenAttemptStartedMinutesAgo(95);
        assertThrows(SubmissionTooLateException.class, () -> service.submit(EXAM_ID, USER_ID, answers("A", "ĐSĐS")));
    }

    @Test
    void submit_withinTimeIsGradedWithPartialCredit() {
        givenAttemptStartedMinutesAgo(30);
        when(submissionRepository.save(any(Submission.class))).thenAnswer(inv -> inv.getArgument(0));

        // câu 1 đúng (1đ), câu 2 đúng 3/4 ý (0,5đ) → (1 + 0,5) * 10 / 2 = 7,5
        SubmissionResponse response = service.submit(EXAM_ID, USER_ID, answers("A", "ĐSĐĐ"));

        assertEquals(7.5, response.getScore());
        assertEquals(1, response.getCorrectCount());
        assertEquals(2, response.getTotalQuestions());
        assertTrue(response.getResults().get(0).isCorrect());
    }
}
