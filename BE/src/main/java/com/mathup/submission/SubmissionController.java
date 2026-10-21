package com.mathup.submission;

import com.mathup.submission.dto.AttemptResponse;
import com.mathup.submission.dto.SubmissionRequest;
import com.mathup.submission.dto.SubmissionResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/api/exams/{examId}/start")
    public AttemptResponse start(@PathVariable Long examId, @AuthenticationPrincipal Jwt jwt) {
        return submissionService.start(examId, userId(jwt));
    }

    @PostMapping("/api/exams/{examId}/submissions")
    public ResponseEntity<SubmissionResponse> submit(
            @PathVariable Long examId,
            @Valid @RequestBody SubmissionRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(submissionService.submit(examId, userId(jwt), request));
    }

    @GetMapping("/api/submissions/{id}")
    public SubmissionResponse getById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return submissionService.getById(id, userId(jwt));
    }

    @GetMapping("/api/submissions/me")
    public List<SubmissionResponse> getMine(@AuthenticationPrincipal Jwt jwt) {
        return submissionService.getMine(userId(jwt));
    }

    private static Long userId(Jwt jwt) {
        return Long.valueOf(jwt.getSubject());
    }
}
