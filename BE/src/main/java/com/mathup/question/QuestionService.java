package com.mathup.question;

import com.mathup.question.dto.BulkQuestionRequest;
import com.mathup.question.dto.QuestionRequest;
import com.mathup.question.dto.QuestionResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionResponse create(QuestionRequest request) {
        Question question = fromRequest(new Question(), request);
        return QuestionResponse.from(questionRepository.save(question));
    }

    public List<QuestionResponse> createBulk(BulkQuestionRequest request) {
        List<Question> questions = request.getQuestions().stream()
                .map(q -> fromRequest(new Question(), q))
                .toList();
        return questionRepository.saveAll(questions).stream()
                .map(QuestionResponse::from)
                .toList();
    }

    public QuestionResponse update(Long id, QuestionRequest request) {
        Question question = getQuestionOrThrow(id);
        fromRequest(question, request);
        question.setVerified(false);
        return QuestionResponse.from(questionRepository.save(question));
    }

    public QuestionResponse verify(Long id) {
        Question question = getQuestionOrThrow(id);
        question.setVerified(true);
        return QuestionResponse.from(questionRepository.save(question));
    }

    public QuestionResponse getById(Long id) {
        return QuestionResponse.from(getQuestionOrThrow(id));
    }

    public List<QuestionResponse> getAll() {
        return questionRepository.findAll().stream()
                .map(QuestionResponse::from)
                .toList();
    }

    public List<QuestionResponse> getPending() {
        return questionRepository.findByVerifiedFalse().stream()
                .map(QuestionResponse::from)
                .toList();
    }

    private Question getQuestionOrThrow(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
    }

    private Question fromRequest(Question question, QuestionRequest request) {
        question.setContent(request.getContent());
        question.setQuestionType(request.getQuestionType());
        question.setOptions(request.getOptions());
        question.setCorrectAnswer(request.getCorrectAnswer());
        question.setHint(request.getHint());
        question.setExplanation(request.getExplanation());
        question.setChapter(request.getChapter());
        question.setTopic(request.getTopic());
        question.setDifficulty(request.getDifficulty());
        return question;
    }
}
