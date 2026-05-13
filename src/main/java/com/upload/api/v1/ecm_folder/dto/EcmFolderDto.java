package com.upload.api.v1.ecm_folder.dto;

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
public class EcmFolderDto implements Serializable {
  private Long id;

  private String businessType;

  private String folderPath;

  private String status;

  private String createdBy;

  private Instant createdAt;

  private Instant updatedAt;

  private String folderPathSub;

  private String isExist;

  private String isDel;

  private Long tenantId;

  private Long storeId;

  private Long brandId;
}
