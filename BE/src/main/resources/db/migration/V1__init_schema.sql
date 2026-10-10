CREATE TABLE users (
    id            BIGSERIAL PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name     VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP(6) WITH TIME ZONE
);

CREATE TABLE questions (
    id             BIGSERIAL PRIMARY KEY,
    content        TEXT         NOT NULL,
    question_type  VARCHAR(255) NOT NULL CHECK (question_type IN ('MULTIPLE_CHOICE', 'TRUE_FALSE', 'SHORT_ANSWER')),
    correct_answer VARCHAR(255) NOT NULL,
    hint           TEXT,
    explanation    TEXT,
    chapter        VARCHAR(255),
    topic          VARCHAR(255),
    difficulty     VARCHAR(255) CHECK (difficulty IN ('NHAN_BIET', 'THONG_HIEU', 'VAN_DUNG')),
    verified       BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at     TIMESTAMP(6) WITH TIME ZONE
);

CREATE TABLE question_options (
    question_id BIGINT NOT NULL REFERENCES questions (id),
    option_text VARCHAR(255)
);

CREATE TABLE exams (
    id               BIGSERIAL PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    description      TEXT,
    duration_minutes INTEGER      NOT NULL,
    created_at       TIMESTAMP(6) WITH TIME ZONE
);

CREATE TABLE exam_questions (
    exam_id     BIGINT  NOT NULL REFERENCES exams (id),
    question_id BIGINT  NOT NULL REFERENCES questions (id),
    position    INTEGER NOT NULL,
    PRIMARY KEY (exam_id, position)
);

CREATE TABLE submissions (
    id              BIGSERIAL PRIMARY KEY,
    exam_id         BIGINT           NOT NULL REFERENCES exams (id),
    user_id         BIGINT           NOT NULL REFERENCES users (id),
    correct_count   INTEGER          NOT NULL,
    total_questions INTEGER          NOT NULL,
    score           DOUBLE PRECISION NOT NULL,
    submitted_at    TIMESTAMP(6) WITH TIME ZONE
);

CREATE TABLE submission_answers (
    submission_id BIGINT NOT NULL REFERENCES submissions (id),
    question_id   BIGINT NOT NULL,
    answer        VARCHAR(255),
    PRIMARY KEY (submission_id, question_id)
);
