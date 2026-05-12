CREATE TABLE ecm_upload_chunk
(
    id            BIGSERIAL PRIMARY KEY,
    chunk_index   NUMERIC(10, 0),
    chunk_size_mb NUMERIC(10, 0),
    status        VARCHAR(20),
    created_at    TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    upload_id     BIGINT
);