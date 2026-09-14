package com.mathup.exam.dto;

import com.mathup.exam.Exam;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExamSummaryResponse {

    private Long id;
    private String title;
    private String description;
    private Integer durationMinutes;
    private int questionCount;
    private Instant createdAt;

    public static ExamSummaryResponse from(Exam exam) {
        return ExamSummaryResponse.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .description(exam.getDescription())
                .durationMinutes(exam.getDurationMinutes())
                .questionCount(exam.getQuestions().size())
                .createdAt(exam.getCreatedAt())
                .build();
    }
}
