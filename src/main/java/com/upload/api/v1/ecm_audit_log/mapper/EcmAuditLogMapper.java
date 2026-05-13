package com.upload.api.v1.ecm_audit_log.mapper;

import com.upload.api.v1.ecm_audit_log.dto.EcmAuditLogDto;
import com.upload.api.v1.ecm_audit_log.entity.EcmAuditLog;
import com.vn.lib.common.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {}
)
public interface EcmAuditLogMapper extends EntityMapper<EcmAuditLogDto, EcmAuditLog> {
}
