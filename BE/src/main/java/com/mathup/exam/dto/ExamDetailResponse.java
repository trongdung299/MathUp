package com.mathup.exam.dto;

import com.mathup.exam.Exam;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExamDetailResponse {

    private Long id;
    private String title;
    private String description;
    private Integer durationMinutes;
    private List<ExamQuestionView> questions;

    public static ExamDetailResponse from(Exam exam) {
        return ExamDetailResponse.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .description(exam.getDescription())
                .durationMinutes(exam.getDurationMinutes())
                .questions(exam.getQuestions().stream().map(ExamQuestionView::from).toList())
                .build();
    }
}
