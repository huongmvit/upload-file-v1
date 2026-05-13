package com.upload.api.v1.ecm_upload.repo;

import com.upload.api.v1.ecm_upload.entity.EcmUpload;
import java.lang.Long;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcmUploadRepos extends JpaRepository<EcmUpload, Long> {
}
