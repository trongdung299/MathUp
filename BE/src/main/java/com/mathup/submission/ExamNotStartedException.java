package com.mathup.submission;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExamNotStartedException extends RuntimeException {

    public ExamNotStartedException(Long examId) {
        super("Exam " + examId + " has not been started — call POST /api/exams/" + examId + "/start first");
    }
}
