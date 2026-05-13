package com.upload.api.v1.ecm_folder.mapper.impl;

import com.upload.api.v1.ecm_folder.dto.EcmFolderDto;
import com.upload.api.v1.ecm_folder.entity.EcmFolder;
import com.upload.api.v1.ecm_folder.mapper.EcmFolderMapper;
import java.lang.Override;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EcmFolderMapperImpl implements EcmFolderMapper {
  private final ModelMapper modelMapper;

  @Override
  public EcmFolder toEntity(EcmFolderDto dto) {
    return this.modelMapper.map(dto, EcmFolder.class);
  }

  @Override
  public EcmFolderDto toDto(EcmFolder entity) {
    return this.modelMapper.map(entity, EcmFolderDto.class);
  }

  @Override
  public List<EcmFolder> toEntity(List<EcmFolderDto> toEntityList) {
    return this.modelMapper.map(toEntityList, new org.modelmapper.TypeToken<List<EcmFolder>>() {}.getType());
  }

  @Override
  public List<EcmFolderDto> toDto(List<EcmFolder> toDtoList) {
    return this.modelMapper.map(toDtoList, new org.modelmapper.TypeToken<List<EcmFolderDto>>() {}.getType());
  }
}
