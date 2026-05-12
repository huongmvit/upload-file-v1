CREATE TABLE ecm_upload
(
    id             BIGSERIAL PRIMARY KEY,
    trace_id       VARCHAR(100),
    document_class VARCHAR(255),
    mime_type      VARCHAR(100),
    upload_status  VARCHAR(20),
    created_by     VARCHAR(100),
    created_at     TIMESTAMP(6)    DEFAULT CURRENT_TIMESTAMP,
    is_del         INTEGER         DEFAULT 0,
    object_store   VARCHAR(100),
    file_draft     VARCHAR(20),
    ocr            VARCHAR(20),
    folder_path    VARCHAR(255),
    document       VARCHAR(255),
    file_size      NUMERIC(12, 0)  DEFAULT 0,
    url            VARCHAR(255),
    tenant_id      BIGINT NOT NULL DEFAULT 0,
    store_id       BIGINT NOT NULL DEFAULT 0,
    brand_id       BIGINT NOT NULL DEFAULT 0
);