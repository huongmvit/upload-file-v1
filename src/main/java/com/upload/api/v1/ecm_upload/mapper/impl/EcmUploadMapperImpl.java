package com.upload.api.v1.ecm_upload.mapper.impl;

import com.upload.api.v1.ecm_upload.dto.EcmUploadDto;
import com.upload.api.v1.ecm_upload.entity.EcmUpload;
import com.upload.api.v1.ecm_upload.mapper.EcmUploadMapper;
import java.lang.Override;
import java.util.List;
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
}
