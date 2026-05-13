package com.upload.api.v1.ecm_upload_chunk.mapper;

import com.upload.api.v1.ecm_upload_chunk.dto.EcmUploadChunkDto;
import com.upload.api.v1.ecm_upload_chunk.entity.EcmUploadChunk;
import com.vn.lib.common.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {}
)
public interface EcmUploadChunkMapper extends EntityMapper<EcmUploadChunkDto, EcmUploadChunk> {
}
