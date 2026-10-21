package com.mathup.exam;

import com.mathup.exam.dto.ExamDetailResponse;
import com.mathup.exam.dto.ExamRequest;
import com.mathup.exam.dto.ExamSummaryResponse;
import com.mathup.question.Question;
import com.mathup.question.QuestionNotFoundException;
import com.mathup.question.QuestionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    public ExamDetailResponse create(ExamRequest request) {
        List<Question> questions = request.getQuestionIds().stream()
                .map(id -> questionRepository.findById(id)
                        .orElseThrow(() -> new QuestionNotFoundException(id)))
                .toList();

        List<Long> unverifiedIds = questions.stream()
                .filter(q -> !q.isVerified())
                .map(Question::getId)
                .toList();
        if (!unverifiedIds.isEmpty()) {
            throw new UnverifiedQuestionException(unverifiedIds);
        }

        Exam exam = Exam.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .durationMinutes(request.getDurationMinutes())
                .questions(questions)
                .build();

        return ExamDetailResponse.from(examRepository.save(exam));
    }

    public ExamDetailResponse getById(Long id) {
        return ExamDetailResponse.from(getExamOrThrow(id));
    }

    public List<ExamSummaryResponse> getAll() {
        return examRepository.findAll().stream().map(ExamSummaryResponse::from).toList();
    }

    Exam getExamOrThrow(Long id) {
        return examRepository.findById(id).orElseThrow(() -> new ExamNotFoundException(id));
    }
}
