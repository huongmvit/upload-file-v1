package com.upload.api.v1.ecm_upload.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = EcmUpload.TABLE_NAME
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EcmUpload implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "ecm_upload";

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "trace_id"
    )
    private String traceId;

    @Column(
            name = "document_class"
    )
    private String documentClass;

    @Column(
            name = "mime_type"
    )
    private String mimeType;

    @Column(
            name = "upload_status"
    )
    private String uploadStatus;

    @Column(
            name = "created_by"
    )
    private String createdBy;

    @Column(
            name = "created_at"
    )
    private Instant createdAt;

    @Column(
            name = "is_del"
    )
    private String isDel;

    @Column(
            name = "object_store"
    )
    private String objectStore;

    @Column(
            name = "file_draft"
    )
    private String fileDraft;

    @Column(
            name = "ocr"
    )
    private String ocr;

    @Column(
            name = "folder_path"
    )
    private String folderPath;

    @Column(
            name = "document"
    )
    private String document;

    @Column(
            name = "file_size"
    )
    private BigDecimal fileSize;

    @Column(
            name = "url"
    )
    private String url;

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
}
