package com.mathup.question.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BulkQuestionRequest {

    @NotEmpty
    @Valid
    private List<QuestionRequest> questions;
}
