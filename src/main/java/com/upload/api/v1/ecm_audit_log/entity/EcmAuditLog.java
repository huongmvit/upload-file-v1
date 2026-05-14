package com.upload.api.v1.ecm_audit_log.entity;

import com.upload.api.v1.enums.AuditStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import lombok.*;

@Entity
@Table(
        name = EcmAuditLog.TABLE_NAME
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EcmAuditLog implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "ecm_audit_log";

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "url_api"
    )
    private String urlApi;

    @Column(
            name = "action"
    )
    private String action;

    @Column(
            name = "error_code"
    )
    private String errorCode;

    @Column(
            name = "status"
    )
    @Enumerated(EnumType.STRING)
    private AuditStatus status;

    @Column(
            name = "created_at"
    )
    private Instant createdAt;

    @Column(
            name = "last_retry_at"
    )
    private Instant lastRetryAt;

    @Column(
            name = "trace_id"
    )
    private String traceId;

    @Column(
            name = "error_message"
    )
    private String errorMessage;

    @Column(
            name = "retry_count"
    )
    private Integer retryCount;
}
