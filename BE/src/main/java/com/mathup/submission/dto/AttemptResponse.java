package com.mathup.submission.dto;

import com.mathup.submission.ExamAttempt;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AttemptResponse {

    private Long attemptId;
    private Long examId;
    private Instant startedAt;
    private Instant deadline;

    public static AttemptResponse from(ExamAttempt attempt) {
        return AttemptResponse.builder()
                .attemptId(attempt.getId())
                .examId(attempt.getExam().getId())
                .startedAt(attempt.getStartedAt())
                .deadline(attempt.deadline())
                .build();
    }
}
