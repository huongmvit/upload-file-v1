CREATE TABLE ecm_folder
(
    id              BIGSERIAL PRIMARY KEY,
    business_type   VARCHAR(50),
    folder_path     VARCHAR(255),
    status          VARCHAR(20),
    created_by      VARCHAR(50),
    created_at      TIMESTAMP(6)    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP(6)    DEFAULT CURRENT_TIMESTAMP,
    folder_path_sub VARCHAR(255),
    is_exist        INTEGER         DEFAULT 0,
    is_del          INTEGER         DEFAULT 0,
    tenant_id       BIGINT NOT NULL DEFAULT 0,
    store_id        BIGINT NOT NULL DEFAULT 0,
    brand_id        BIGINT NOT NULL DEFAULT 0
);