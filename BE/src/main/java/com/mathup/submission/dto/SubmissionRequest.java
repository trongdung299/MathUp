package com.mathup.submission.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionRequest {

    @NotEmpty
    private Map<Long, String> answers;
}
