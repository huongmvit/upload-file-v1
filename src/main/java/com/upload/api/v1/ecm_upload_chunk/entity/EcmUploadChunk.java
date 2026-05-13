package com.upload.api.v1.ecm_upload_chunk.entity;

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
        name = EcmUploadChunk.TABLE_NAME
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EcmUploadChunk implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "ecm_upload_chunk";

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "chunk_index"
    )
    private BigDecimal chunkIndex;

    @Column(
            name = "chunk_size_mb"
    )
    private BigDecimal chunkSizeMb;

    @Column(
            name = "status"
    )
    private String status;

    @Column(
            name = "created_at"
    )
    private Instant createdAt;

    @Column(
            name = "upload_id"
    )
    private Long uploadId;
}
