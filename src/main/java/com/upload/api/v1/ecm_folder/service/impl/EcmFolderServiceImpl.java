package com.upload.api.v1.ecm_folder.service.impl;

import com.upload.api.v1.ecm_folder.dto.EcmFolderDto;
import com.upload.api.v1.ecm_folder.entity.EcmFolder;
import com.upload.api.v1.ecm_folder.mapper.EcmFolderMapper;
import com.upload.api.v1.ecm_folder.repo.EcmFolderRepos;
import com.upload.api.v1.ecm_folder.service.EcmFolderService;
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
public class EcmFolderServiceImpl implements EcmFolderService {
  private final EcmFolderRepos ecmFolderRepos;

  private final EcmFolderMapper ecmFolderMapper;

  public ObjectPaging list(Pageable pageable) {
    Page<EcmFolder> messagePage = this.ecmFolderRepos.findAll(pageable);
    return CMNAppUtils.pagingResponse(messagePage);
  }

  public Boolean create(Map<String, Object> req) {
    EcmFolderDto dto = CMNConvertUtils.convertMapToClass(req, EcmFolderDto.class);
    return this.saveDatabase(dto) != null;
  }

  public Boolean edit(Map<String, Object> req) {
    EcmFolderDto dto = CMNConvertUtils.convertMapToClass(req, EcmFolderDto.class);
    return this.saveDatabase(dto) != null;
  }

  public Boolean delete(Long id) {
    Optional<EcmFolder> servicesOpt = this.ecmFolderRepos.findById(id);
    return servicesOpt.isPresent();
  }

  @Override
  public EcmFolderDto detail(Long id) {
    Optional<EcmFolder> opt = this.ecmFolderRepos.findById(id);
    if (opt.isPresent()) {
      return this.ecmFolderMapper.toDto(opt.get());
    }
    return null;
  }

  public Boolean saveDatabase(EcmFolderDto dto) {
    EcmFolder entity = this.ecmFolderRepos.save(this.ecmFolderMapper.toEntity(dto));
    return entity != null;
  }

  public EcmFolder saveEntity(EcmFolderDto dto) {
    return this.ecmFolderRepos.save(this.ecmFolderMapper.toEntity(dto));
  }
}
