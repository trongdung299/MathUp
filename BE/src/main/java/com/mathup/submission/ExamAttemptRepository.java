package com.mathup.submission;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {

    Optional<ExamAttempt> findFirstByExamIdAndUserIdAndSubmittedFalseOrderByStartedAtDesc(Long examId, Long userId);
}
