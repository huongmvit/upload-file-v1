package com.upload.api.v1.ecm_upload_chunk.service.impl;

import com.upload.api.v1.ecm_upload_chunk.dto.EcmUploadChunkDto;
import com.upload.api.v1.ecm_upload_chunk.entity.EcmUploadChunk;
import com.upload.api.v1.ecm_upload_chunk.mapper.EcmUploadChunkMapper;
import com.upload.api.v1.ecm_upload_chunk.repo.EcmUploadChunkRepos;
import com.upload.api.v1.ecm_upload_chunk.service.EcmUploadChunkService;
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
public class EcmUploadChunkServiceImpl implements EcmUploadChunkService {
  private final EcmUploadChunkRepos ecmUploadChunkRepos;

  private final EcmUploadChunkMapper ecmUploadChunkMapper;

  public ObjectPaging list(Pageable pageable) {
    Page<EcmUploadChunk> messagePage = this.ecmUploadChunkRepos.findAll(pageable);
    return CMNAppUtils.pagingResponse(messagePage);
  }

  public Boolean create(Map<String, Object> req) {
    EcmUploadChunkDto dto = CMNConvertUtils.convertMapToClass(req, EcmUploadChunkDto.class);
    return this.saveDatabase(dto) != null;
  }

  public Boolean edit(Map<String, Object> req) {
    EcmUploadChunkDto dto = CMNConvertUtils.convertMapToClass(req, EcmUploadChunkDto.class);
    return this.saveDatabase(dto) != null;
  }

  public Boolean delete(Long id) {
    Optional<EcmUploadChunk> servicesOpt = this.ecmUploadChunkRepos.findById(id);
    return servicesOpt.isPresent();
  }

  @Override
  public EcmUploadChunkDto detail(Long id) {
    Optional<EcmUploadChunk> opt = this.ecmUploadChunkRepos.findById(id);
    if (opt.isPresent()) {
      return this.ecmUploadChunkMapper.toDto(opt.get());
    }
    return null;
  }

  public Boolean saveDatabase(EcmUploadChunkDto dto) {
    EcmUploadChunk entity = this.ecmUploadChunkRepos.save(this.ecmUploadChunkMapper.toEntity(dto));
    return entity != null;
  }

  public EcmUploadChunk saveEntity(EcmUploadChunkDto dto) {
    return this.ecmUploadChunkRepos.save(this.ecmUploadChunkMapper.toEntity(dto));
  }
}
