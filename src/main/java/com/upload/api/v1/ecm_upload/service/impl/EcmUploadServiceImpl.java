package com.upload.api.v1.ecm_upload.service.impl;

import com.upload.api.v1.ecm_upload.dto.EcmUploadDto;
import com.upload.api.v1.ecm_upload.entity.EcmUpload;
import com.upload.api.v1.ecm_upload.mapper.EcmUploadMapper;
import com.upload.api.v1.ecm_upload.repo.EcmUploadRepos;
import com.upload.api.v1.ecm_upload.service.EcmUploadService;
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
public class EcmUploadServiceImpl implements EcmUploadService {
  private final EcmUploadRepos ecmUploadRepos;

  private final EcmUploadMapper ecmUploadMapper;

  public ObjectPaging list(Pageable pageable) {
    Page<EcmUpload> messagePage = this.ecmUploadRepos.findAll(pageable);
    return CMNAppUtils.pagingResponse(messagePage);
  }

  public Boolean create(Map<String, Object> req) {
    EcmUploadDto dto = CMNConvertUtils.convertMapToClass(req, EcmUploadDto.class);
    return this.saveDatabase(dto) != null;
  }

  public Boolean edit(Map<String, Object> req) {
    EcmUploadDto dto = CMNConvertUtils.convertMapToClass(req, EcmUploadDto.class);
    return this.saveDatabase(dto) != null;
  }

  public Boolean delete(Long id) {
    Optional<EcmUpload> servicesOpt = this.ecmUploadRepos.findById(id);
    return servicesOpt.isPresent();
  }

  @Override
  public EcmUploadDto detail(Long id) {
    Optional<EcmUpload> opt = this.ecmUploadRepos.findById(id);
    if (opt.isPresent()) {
      return this.ecmUploadMapper.toDto(opt.get());
    }
    return null;
  }

  public Boolean saveDatabase(EcmUploadDto dto) {
    EcmUpload entity = this.ecmUploadRepos.save(this.ecmUploadMapper.toEntity(dto));
    return entity != null;
  }

  public EcmUpload saveEntity(EcmUploadDto dto) {
    return this.ecmUploadRepos.save(this.ecmUploadMapper.toEntity(dto));
  }
}
