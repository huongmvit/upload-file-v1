package com.upload.api.v1.ecm_folder.mapper;

import com.upload.api.v1.ecm_folder.dto.EcmFolderDto;
import com.upload.api.v1.ecm_folder.entity.EcmFolder;
import com.upload.api.v1.ecm_upload.entity.EcmUpload;
import com.vn.lib.common.mapper.EntityMapper;
import com.vn.lib.iam.auth.AesKeyDto;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {}
)
public interface EcmFolderMapper extends EntityMapper<EcmFolderDto, EcmFolder> {
    EcmFolder mapEcmUploadToEcmFolder(String folderPath, AesKeyDto aesKeyDto);
}
