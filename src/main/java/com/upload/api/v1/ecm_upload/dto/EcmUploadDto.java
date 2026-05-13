package com.upload.api.v1.ecm_upload.dto;

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
public class EcmUploadDto implements Serializable {
  private Long id;

  private String traceId;

  private String documentClass;

  private String mimeType;

  private String uploadStatus;

  private String createdBy;

  private Instant createdAt;

  private String isDel;

  private String objectStore;

  private String fileDraft;

  private String ocr;

  private String folderPath;

  private String document;

  private BigDecimal fileSize;

  private String url;

  private Long tenantId;

  private Long storeId;

  private Long brandId;
}
