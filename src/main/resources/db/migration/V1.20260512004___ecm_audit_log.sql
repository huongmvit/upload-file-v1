CREATE TABLE ecm_audit_log
(
    id            BIGSERIAL PRIMARY KEY,
    url_api       VARCHAR(500),
    action        VARCHAR(50),
    error_code    VARCHAR(100),
    status        VARCHAR(20),
    created_at    TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    last_retry_at TIMESTAMP(6),
    trace_id      VARCHAR(100),
    error_message TEXT,
    retry_count   INTEGER      DEFAULT 0
);