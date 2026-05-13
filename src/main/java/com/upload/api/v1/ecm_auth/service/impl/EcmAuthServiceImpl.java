package com.upload.api.v1.ecm_auth.service.impl;

import com.upload.api.v1.ecm_auth.dto.EcmAuthDto;
import com.upload.api.v1.ecm_auth.entity.EcmAuth;
import com.upload.api.v1.ecm_auth.mapper.EcmAuthMapper;
import com.upload.api.v1.ecm_auth.repo.EcmAuthRepos;
import com.upload.api.v1.ecm_auth.service.EcmAuthService;
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
public class EcmAuthServiceImpl implements EcmAuthService {
  private final EcmAuthRepos ecmAuthRepos;

  private final EcmAuthMapper ecmAuthMapper;

  public ObjectPaging list(Pageable pageable) {
    Page<EcmAuth> messagePage = this.ecmAuthRepos.findAll(pageable);
    return CMNAppUtils.pagingResponse(messagePage);
  }

  public Boolean create(Map<String, Object> req) {
    EcmAuthDto dto = CMNConvertUtils.convertMapToClass(req, EcmAuthDto.class);
    return this.saveDatabase(dto) != null;
  }

  public Boolean edit(Map<String, Object> req) {
    EcmAuthDto dto = CMNConvertUtils.convertMapToClass(req, EcmAuthDto.class);
    return this.saveDatabase(dto) != null;
  }

  public Boolean delete(Long id) {
    Optional<EcmAuth> servicesOpt = this.ecmAuthRepos.findById(id);
    return servicesOpt.isPresent();
  }

  @Override
  public EcmAuthDto detail(Long id) {
    Optional<EcmAuth> opt = this.ecmAuthRepos.findById(id);
    if (opt.isPresent()) {
      return this.ecmAuthMapper.toDto(opt.get());
    }
    return null;
  }

  public Boolean saveDatabase(EcmAuthDto dto) {
    EcmAuth entity = this.ecmAuthRepos.save(this.ecmAuthMapper.toEntity(dto));
    return entity != null;
  }

  public EcmAuth saveEntity(EcmAuthDto dto) {
    return this.ecmAuthRepos.save(this.ecmAuthMapper.toEntity(dto));
  }
}
