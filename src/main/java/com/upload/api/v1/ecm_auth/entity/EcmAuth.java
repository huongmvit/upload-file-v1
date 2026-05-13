package com.upload.api.v1.ecm_auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = EcmAuth.TABLE_NAME
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EcmAuth implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "ecm_auth";

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "tenant_id"
    )
    private Long tenantId;

    @Column(
            name = "store_id"
    )
    private Long storeId;

    @Column(
            name = "brand_id"
    )
    private Long brandId;

    @Column(
            name = "client_id"
    )
    private String clientId;

    @Column(
            name = "server_url"
    )
    private String serverUrl;

    @Column(
            name = "client_secret"
    )
    private String clientSecret;

    @Column(
            name = "status"
    )
    private String status;

    @Column(
            name = "created_at"
    )
    private Instant createdAt;
}
