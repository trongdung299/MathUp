package com.mathup.question.dto;

import com.mathup.question.Difficulty;
import com.mathup.question.Question;
import com.mathup.question.QuestionType;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResponse {

    private Long id;
    private String content;
    private QuestionType questionType;
    private List<String> options;
    private String correctAnswer;
    private String hint;
    private String explanation;
    private String chapter;
    private String topic;
    private Difficulty difficulty;
    private boolean verified;
    private Instant createdAt;

    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .questionType(question.getQuestionType())
                .options(question.getOptions())
                .correctAnswer(question.getCorrectAnswer())
                .hint(question.getHint())
                .explanation(question.getExplanation())
                .chapter(question.getChapter())
                .topic(question.getTopic())
                .difficulty(question.getDifficulty())
                .verified(question.isVerified())
                .createdAt(question.getCreatedAt())
                .build();
    }
}
