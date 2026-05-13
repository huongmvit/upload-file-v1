package com.upload.api.v1.ecm_auth.mapper;

import com.upload.api.v1.ecm_auth.dto.EcmAuthDto;
import com.upload.api.v1.ecm_auth.entity.EcmAuth;
import com.vn.lib.common.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {}
)
public interface EcmAuthMapper extends EntityMapper<EcmAuthDto, EcmAuth> {
}
