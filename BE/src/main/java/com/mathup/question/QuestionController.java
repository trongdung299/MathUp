package com.mathup.question;

import com.mathup.question.dto.BulkQuestionRequest;
import com.mathup.question.dto.QuestionRequest;
import com.mathup.question.dto.QuestionResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponse> create(@Valid @RequestBody QuestionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.create(request));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<QuestionResponse>> createBulk(@Valid @RequestBody BulkQuestionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createBulk(request));
    }

    @PutMapping("/{id}")
    public QuestionResponse update(@PathVariable Long id, @Valid @RequestBody QuestionRequest request) {
        return questionService.update(id, request);
    }

    @PatchMapping("/{id}/verify")
    public QuestionResponse verify(@PathVariable Long id) {
        return questionService.verify(id);
    }

    @GetMapping("/{id}")
    public QuestionResponse getById(@PathVariable Long id) {
        return questionService.getById(id);
    }

    @GetMapping
    public List<QuestionResponse> getAll() {
        return questionService.getAll();
    }

    @GetMapping("/pending")
    public List<QuestionResponse> getPending() {
        return questionService.getPending();
    }
}
