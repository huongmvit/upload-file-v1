package com.upload.api.v1.ecm_upload_chunk.repo;

import com.upload.api.v1.ecm_upload_chunk.entity.EcmUploadChunk;
import java.lang.Long;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcmUploadChunkRepos extends JpaRepository<EcmUploadChunk, Long> {
}
