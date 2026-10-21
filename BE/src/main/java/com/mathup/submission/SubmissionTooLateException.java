package com.mathup.submission;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SubmissionTooLateException extends RuntimeException {

    public SubmissionTooLateException(Long examId) {
        super("Time limit for exam " + examId + " has passed — start the exam again");
    }
}
