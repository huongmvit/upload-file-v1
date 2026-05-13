package com.upload.api.v1.ecm_upload_chunk.dto;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EcmUploadChunkDto implements Serializable {
  private Long id;

  private BigDecimal chunkIndex;

  private BigDecimal chunkSizeMb;

  private String status;

  private Instant createdAt;

  private Long uploadId;
}
