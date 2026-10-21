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
    /** Tỉ lệ điểm đạt được của câu này, 0..1 (câu Đúng/Sai nhiều ý có thể được điểm một phần). */
    private double points;
    private String explanation;
}
