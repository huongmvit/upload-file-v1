package com.upload.api.v1.ecm_upload_chunk.mapper.impl;

import com.upload.api.v1.ecm_upload_chunk.dto.EcmUploadChunkDto;
import com.upload.api.v1.ecm_upload_chunk.entity.EcmUploadChunk;
import com.upload.api.v1.ecm_upload_chunk.mapper.EcmUploadChunkMapper;
import java.lang.Override;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EcmUploadChunkMapperImpl implements EcmUploadChunkMapper {
  private final ModelMapper modelMapper;

  @Override
  public EcmUploadChunk toEntity(EcmUploadChunkDto dto) {
    return this.modelMapper.map(dto, EcmUploadChunk.class);
  }

  @Override
  public EcmUploadChunkDto toDto(EcmUploadChunk entity) {
    return this.modelMapper.map(entity, EcmUploadChunkDto.class);
  }

  @Override
  public List<EcmUploadChunk> toEntity(List<EcmUploadChunkDto> toEntityList) {
    return this.modelMapper.map(toEntityList, new org.modelmapper.TypeToken<List<EcmUploadChunk>>() {}.getType());
  }

  @Override
  public List<EcmUploadChunkDto> toDto(List<EcmUploadChunk> toDtoList) {
    return this.modelMapper.map(toDtoList, new org.modelmapper.TypeToken<List<EcmUploadChunkDto>>() {}.getType());
  }
}
