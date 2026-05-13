package com.upload.api.v1.ecm_auth.dto;

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
public class EcmAuthDto implements Serializable {
  private Long id;

  private Long tenantId;

  private Long storeId;

  private Long brandId;

  private String clientId;

  private String serverUrl;

  private String clientSecret;

  private String status;

  private Instant createdAt;
}
