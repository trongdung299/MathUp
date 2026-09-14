package com.mathup.exam;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnverifiedQuestionException extends RuntimeException {

    public UnverifiedQuestionException(List<Long> questionIds) {
        super("Questions not yet verified, cannot be used in an exam: " + questionIds);
    }
}
