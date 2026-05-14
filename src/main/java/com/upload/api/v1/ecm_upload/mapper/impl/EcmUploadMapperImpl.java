package com.upload.api.v1.ecm_upload.mapper.impl;

import com.upload.api.v1.ecm_upload.dto.EcmUploadDto;
import com.upload.api.v1.ecm_upload.entity.EcmUpload;
import com.upload.api.v1.ecm_upload.mapper.EcmUploadMapper;
import java.lang.Override;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.upload.api.v1.enums.UploadStatus;
import com.upload.api.v1.files.req.CreateOneDocumentReq;
import com.upload.constant.UploadConstant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EcmUploadMapperImpl implements EcmUploadMapper {
  private final ModelMapper modelMapper;

  @Override
  public EcmUpload toEntity(EcmUploadDto dto) {
    return this.modelMapper.map(dto, EcmUpload.class);
  }

  @Override
  public EcmUploadDto toDto(EcmUpload entity) {
    return this.modelMapper.map(entity, EcmUploadDto.class);
  }

  @Override
  public List<EcmUpload> toEntity(List<EcmUploadDto> toEntityList) {
    return this.modelMapper.map(toEntityList, new org.modelmapper.TypeToken<List<EcmUpload>>() {}.getType());
  }

  @Override
  public List<EcmUploadDto> toDto(List<EcmUpload> toDtoList) {
    return this.modelMapper.map(toDtoList, new org.modelmapper.TypeToken<List<EcmUploadDto>>() {}.getType());
  }

    @Override
    public EcmUpload mapReqToEcmUpload(CreateOneDocumentReq req, String traceId) {
        EcmUpload ecmUpload = EcmUpload.builder()
                .traceId(traceId)
                .documentClass(req.getDocumentClass())
                .document(req.getDocuments().getOriginalFilename())
                .mimeType(req.getDocuments().getContentType())
                .fileSize(BigDecimal.valueOf(req.getDocuments().getSize()))
                .uploadStatus(UploadStatus.UPLOADING)
//                .createdBy(createdBy)
                .isDel(UploadConstant.IS_NOT_DELETED)
                .objectStore(UUID.randomUUID().toString())
                .fileDraft(req.getFileDraft())
                .ocr(req.getOcr())
                .folderPath(req.getFolderPath())
//                .url(url)
//                .tenantId(tenantId)
//                .storeId(storeId)
//                .brandId(brandId)
                .build();
        return ecmUpload;
    }
}
