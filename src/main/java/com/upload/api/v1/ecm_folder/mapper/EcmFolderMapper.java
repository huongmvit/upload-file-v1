package com.upload.api.v1.ecm_folder.mapper;

import com.upload.api.v1.ecm_folder.dto.EcmFolderDto;
import com.upload.api.v1.ecm_folder.entity.EcmFolder;
import com.vn.lib.common.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {}
)
public interface EcmFolderMapper extends EntityMapper<EcmFolderDto, EcmFolder> {
}
