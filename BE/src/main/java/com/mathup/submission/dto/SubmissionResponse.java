package com.mathup.submission.dto;

import com.mathup.submission.Submission;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubmissionResponse {

    private Long id;
    private Long examId;
    private int correctCount;
    private int totalQuestions;
    private double score;
    private Instant submittedAt;
    private List<QuestionResult> results;

    public static SubmissionResponse from(Submission submission, List<QuestionResult> results) {
        return SubmissionResponse.builder()
                .id(submission.getId())
                .examId(submission.getExam().getId())
                .correctCount(submission.getCorrectCount())
                .totalQuestions(submission.getTotalQuestions())
                .score(submission.getScore())
                .submittedAt(submission.getSubmittedAt())
                .results(results)
                .build();
    }
}
