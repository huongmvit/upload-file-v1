package com.upload.api.v1.ecm_audit_log.service.impl;

import com.upload.api.v1.ecm_audit_log.dto.EcmAuditLogDto;
import com.upload.api.v1.ecm_audit_log.entity.EcmAuditLog;
import com.upload.api.v1.ecm_audit_log.mapper.EcmAuditLogMapper;
import com.upload.api.v1.ecm_audit_log.repo.EcmAuditLogRepos;
import com.upload.api.v1.ecm_audit_log.service.EcmAuditLogService;
import com.vn.lib.common.paging.ObjectPaging;
import com.vn.lib.common.utils.CMNAppUtils;
import com.vn.lib.common.utils.CMNConvertUtils;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EcmAuditLogServiceImpl implements EcmAuditLogService {
  private final EcmAuditLogRepos ecmAuditLogRepos;

  private final EcmAuditLogMapper ecmAuditLogMapper;

  public ObjectPaging list(Pageable pageable) {
    Page<EcmAuditLog> messagePage = this.ecmAuditLogRepos.findAll(pageable);
    return CMNAppUtils.pagingResponse(messagePage);
  }

  public Boolean create(Map<String, Object> req) {
    EcmAuditLogDto dto = CMNConvertUtils.convertMapToClass(req, EcmAuditLogDto.class);
    return this.saveDatabase(dto) != null;
  }

  public Boolean edit(Map<String, Object> req) {
    EcmAuditLogDto dto = CMNConvertUtils.convertMapToClass(req, EcmAuditLogDto.class);
    return this.saveDatabase(dto) != null;
  }

  public Boolean delete(Long id) {
    Optional<EcmAuditLog> servicesOpt = this.ecmAuditLogRepos.findById(id);
    return servicesOpt.isPresent();
  }

  @Override
  public EcmAuditLogDto detail(Long id) {
    Optional<EcmAuditLog> opt = this.ecmAuditLogRepos.findById(id);
    if (opt.isPresent()) {
      return this.ecmAuditLogMapper.toDto(opt.get());
    }
    return null;
  }

  public Boolean saveDatabase(EcmAuditLogDto dto) {
    EcmAuditLog entity = this.ecmAuditLogRepos.save(this.ecmAuditLogMapper.toEntity(dto));
    return entity != null;
  }

  public EcmAuditLog saveEntity(EcmAuditLogDto dto) {
    return this.ecmAuditLogRepos.save(this.ecmAuditLogMapper.toEntity(dto));
  }
}
