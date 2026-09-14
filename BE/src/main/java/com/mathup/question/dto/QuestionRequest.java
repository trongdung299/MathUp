package com.mathup.question.dto;

import com.mathup.question.Difficulty;
import com.mathup.question.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {

    @NotBlank
    private String content;

    @NotNull
    private QuestionType questionType;

    private List<String> options;

    @NotBlank
    private String correctAnswer;

    private String hint;

    private String explanation;

    private String chapter;

    private String topic;

    private Difficulty difficulty;
}
