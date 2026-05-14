package com.upload.api.v1.ecm_upload.mapper;

import com.upload.api.v1.ecm_upload.dto.EcmUploadDto;
import com.upload.api.v1.ecm_upload.entity.EcmUpload;
import com.upload.api.v1.files.req.CreateOneDocumentReq;
import com.vn.lib.common.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {}
)
public interface EcmUploadMapper extends EntityMapper<EcmUploadDto, EcmUpload> {
    EcmUpload mapReqToEcmUpload(CreateOneDocumentReq req, String traceId);
}
