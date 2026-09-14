package com.mathup.submission;

import com.mathup.exam.Exam;
import com.mathup.exam.ExamNotFoundException;
import com.mathup.exam.ExamRepository;
import com.mathup.question.Question;
import com.mathup.submission.dto.QuestionResult;
import com.mathup.submission.dto.SubmissionRequest;
import com.mathup.submission.dto.SubmissionResponse;
import com.mathup.user.User;
import com.mathup.user.UserRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    @Transactional
    public SubmissionResponse submit(Long examId, Long userId, SubmissionRequest request) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new ExamNotFoundException(examId));
        User user = userRepository.getReferenceById(userId);

        Map<Long, String> submittedAnswers = request.getAnswers();
        List<QuestionResult> results = exam.getQuestions().stream()
                .map(question -> gradeQuestion(question, submittedAnswers.get(question.getId())))
                .toList();

        int correctCount = (int) results.stream().filter(QuestionResult::isCorrect).count();
        int totalQuestions = exam.getQuestions().size();
        double score = totalQuestions == 0
                ? 0
                : Math.round((correctCount * 10.0 / totalQuestions) * 100) / 100.0;

        Submission submission = Submission.builder()
                .exam(exam)
                .user(user)
                .answers(submittedAnswers)
                .correctCount(correctCount)
                .totalQuestions(totalQuestions)
                .score(score)
                .build();

        return SubmissionResponse.from(submissionRepository.save(submission), results);
    }

    public SubmissionResponse getById(Long id, Long requesterUserId) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new SubmissionNotFoundException(id));

        if (!submission.getUser().getId().equals(requesterUserId)) {
            throw new ForbiddenException("You may only view your own submissions");
        }

        List<QuestionResult> results = submission.getExam().getQuestions().stream()
                .map(question -> gradeQuestion(question, submission.getAnswers().get(question.getId())))
                .toList();

        return SubmissionResponse.from(submission, results);
    }

    public List<SubmissionResponse> getMine(Long userId) {
        return submissionRepository.findByUserId(userId).stream()
                .map(submission -> {
                    List<QuestionResult> results = submission.getExam().getQuestions().stream()
                            .map(question -> gradeQuestion(question, submission.getAnswers().get(question.getId())))
                            .toList();
                    return SubmissionResponse.from(submission, results);
                })
                .toList();
    }

    private QuestionResult gradeQuestion(Question question, String submittedAnswer) {
        boolean correct = submittedAnswer != null
                && submittedAnswer.trim().equalsIgnoreCase(question.getCorrectAnswer().trim());

        return QuestionResult.builder()
                .questionId(question.getId())
                .submittedAnswer(submittedAnswer)
                .correctAnswer(question.getCorrectAnswer())
                .correct(correct)
                .explanation(question.getExplanation())
                .build();
    }
}
