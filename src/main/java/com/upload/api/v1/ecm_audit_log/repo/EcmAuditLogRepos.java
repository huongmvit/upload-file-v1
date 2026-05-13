package com.upload.api.v1.ecm_audit_log.repo;

import com.upload.api.v1.ecm_audit_log.entity.EcmAuditLog;
import java.lang.Long;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcmAuditLogRepos extends JpaRepository<EcmAuditLog, Long> {
}
