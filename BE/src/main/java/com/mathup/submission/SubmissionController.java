package com.mathup.submission;

import com.mathup.auth.CurrentUser;
import com.mathup.submission.dto.SubmissionRequest;
import com.mathup.submission.dto.SubmissionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/api/exams/{examId}/submissions")
    public ResponseEntity<SubmissionResponse> submit(
            @PathVariable Long examId,
            @Valid @RequestBody SubmissionRequest request,
            HttpServletRequest httpRequest) {
        Long userId = CurrentUser.requireUserId(httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(submissionService.submit(examId, userId, request));
    }

    @GetMapping("/api/submissions/{id}")
    public SubmissionResponse getById(@PathVariable Long id, HttpServletRequest httpRequest) {
        Long userId = CurrentUser.requireUserId(httpRequest);
        return submissionService.getById(id, userId);
    }

    @GetMapping("/api/submissions/me")
    public List<SubmissionResponse> getMine(HttpServletRequest httpRequest) {
        Long userId = CurrentUser.requireUserId(httpRequest);
        return submissionService.getMine(userId);
    }
}
