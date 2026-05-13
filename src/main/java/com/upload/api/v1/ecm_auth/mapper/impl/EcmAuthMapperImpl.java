package com.upload.api.v1.ecm_auth.mapper.impl;

import com.upload.api.v1.ecm_auth.dto.EcmAuthDto;
import com.upload.api.v1.ecm_auth.entity.EcmAuth;
import com.upload.api.v1.ecm_auth.mapper.EcmAuthMapper;
import java.lang.Override;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EcmAuthMapperImpl implements EcmAuthMapper {
  private final ModelMapper modelMapper;

  @Override
  public EcmAuth toEntity(EcmAuthDto dto) {
    return this.modelMapper.map(dto, EcmAuth.class);
  }

  @Override
  public EcmAuthDto toDto(EcmAuth entity) {
    return this.modelMapper.map(entity, EcmAuthDto.class);
  }

  @Override
  public List<EcmAuth> toEntity(List<EcmAuthDto> toEntityList) {
    return this.modelMapper.map(toEntityList, new org.modelmapper.TypeToken<List<EcmAuth>>() {}.getType());
  }

  @Override
  public List<EcmAuthDto> toDto(List<EcmAuth> toDtoList) {
    return this.modelMapper.map(toDtoList, new org.modelmapper.TypeToken<List<EcmAuthDto>>() {}.getType());
  }
}
