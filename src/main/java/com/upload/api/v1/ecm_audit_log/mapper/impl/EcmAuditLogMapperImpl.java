package com.upload.api.v1.ecm_audit_log.mapper.impl;

import com.upload.api.v1.ecm_audit_log.dto.EcmAuditLogDto;
import com.upload.api.v1.ecm_audit_log.entity.EcmAuditLog;
import com.upload.api.v1.ecm_audit_log.mapper.EcmAuditLogMapper;
import java.lang.Override;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EcmAuditLogMapperImpl implements EcmAuditLogMapper {
  private final ModelMapper modelMapper;

  @Override
  public EcmAuditLog toEntity(EcmAuditLogDto dto) {
    return this.modelMapper.map(dto, EcmAuditLog.class);
  }

  @Override
  public EcmAuditLogDto toDto(EcmAuditLog entity) {
    return this.modelMapper.map(entity, EcmAuditLogDto.class);
  }

  @Override
  public List<EcmAuditLog> toEntity(List<EcmAuditLogDto> toEntityList) {
    return this.modelMapper.map(toEntityList, new org.modelmapper.TypeToken<List<EcmAuditLog>>() {}.getType());
  }

  @Override
  public List<EcmAuditLogDto> toDto(List<EcmAuditLog> toDtoList) {
    return this.modelMapper.map(toDtoList, new org.modelmapper.TypeToken<List<EcmAuditLogDto>>() {}.getType());
  }
}
