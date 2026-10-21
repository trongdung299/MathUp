CREATE TABLE exam_attempts (
    id         BIGSERIAL PRIMARY KEY,
    exam_id    BIGINT       NOT NULL REFERENCES exams (id),
    user_id    BIGINT       NOT NULL REFERENCES users (id),
    started_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    submitted  BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_exam_attempts_user_exam ON exam_attempts (user_id, exam_id);
