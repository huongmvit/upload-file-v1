package com.upload.api.v1.ecm_upload.mapper;

import com.upload.api.v1.ecm_upload.dto.EcmUploadDto;
import com.upload.api.v1.ecm_upload.entity.EcmUpload;
import com.vn.lib.common.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {}
)
public interface EcmUploadMapper extends EntityMapper<EcmUploadDto, EcmUpload> {
}
