package com.upload.api.v1.ecm_upload_chunk.service;

import com.upload.api.v1.ecm_upload_chunk.dto.EcmUploadChunkDto;
import com.vn.lib.common.paging.ObjectPaging;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.Map;
import org.springframework.data.domain.Pageable;

public interface EcmUploadChunkService {
  ObjectPaging list(Pageable pageable);

  Boolean create(Map<String, Object> body);

  Boolean edit(Map<String, Object> body);

  Boolean delete(Long id);

  EcmUploadChunkDto detail(Long id);
}
