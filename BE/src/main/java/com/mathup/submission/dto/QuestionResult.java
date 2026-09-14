package com.mathup.submission.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionResult {

    private Long questionId;
    private String submittedAnswer;
    private String correctAnswer;
    private boolean correct;
    private String explanation;
}
