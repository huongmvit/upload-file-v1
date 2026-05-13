CREATE TABLE ecm_auth
(
    id            BIGSERIAL PRIMARY KEY,
    tenant_id     BIGINT NOT NULL DEFAULT 0,
    store_id      BIGINT NOT NULL DEFAULT 0,
    brand_id      BIGINT NOT NULL DEFAULT 0,
    client_id     varchar(255) NULL,
    server_url    varchar(255) NULL,
    client_secret varchar(255) NULL,
    status        varchar(15) NULL,
    created_at    timestamp NULL DEFAULT CURRENT_TIMESTAMP
);