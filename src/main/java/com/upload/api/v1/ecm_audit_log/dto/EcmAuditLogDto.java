package com.upload.api.v1.ecm_audit_log.dto;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EcmAuditLogDto implements Serializable {
  private Long id;

  private String urlApi;

  private String action;

  private String errorCode;

  private String status;

  private Instant createdAt;

  private Instant lastRetryAt;

  private String traceId;

  private String errorMessage;

  private String retryCount;
}
