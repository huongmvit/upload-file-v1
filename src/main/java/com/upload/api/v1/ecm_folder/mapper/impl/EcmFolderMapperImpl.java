package com.upload.api.v1.ecm_folder.mapper.impl;

import com.upload.api.v1.ecm_folder.dto.EcmFolderDto;
import com.upload.api.v1.ecm_folder.entity.EcmFolder;
import com.upload.api.v1.ecm_folder.mapper.EcmFolderMapper;
import java.lang.Override;
import java.util.List;

import com.upload.api.v1.ecm_upload.entity.EcmUpload;
import com.upload.api.v1.enums.FolderStatus;
import com.upload.constant.UploadConstant;
import com.vn.lib.iam.auth.AesKeyDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EcmFolderMapperImpl implements EcmFolderMapper {
  private final ModelMapper modelMapper;

  @Override
  public EcmFolder toEntity(EcmFolderDto dto) {
    return this.modelMapper.map(dto, EcmFolder.class);
  }

  @Override
  public EcmFolderDto toDto(EcmFolder entity) {
    return this.modelMapper.map(entity, EcmFolderDto.class);
  }

  @Override
  public List<EcmFolder> toEntity(List<EcmFolderDto> toEntityList) {
    return this.modelMapper.map(toEntityList, new org.modelmapper.TypeToken<List<EcmFolder>>() {}.getType());
  }

  @Override
  public List<EcmFolderDto> toDto(List<EcmFolder> toDtoList) {
    return this.modelMapper.map(toDtoList, new org.modelmapper.TypeToken<List<EcmFolderDto>>() {}.getType());
  }

    @Override
    public EcmFolder mapEcmUploadToEcmFolder(String folderPath, AesKeyDto aesKeyDto) {
        EcmFolder ecmFolder = new EcmFolder();
        ecmFolder.setFolderPath(folderPath);
        ecmFolder.setIsExist(UploadConstant.IS_NOT_EXIST);
        ecmFolder.setStatus(FolderStatus.INIT);
        ecmFolder.setTenantId(aesKeyDto.getTenantId());
        ecmFolder.setStoreId(0L);
        ecmFolder.setBrandId(aesKeyDto.getBrandId());
        return ecmFolder;
    }
}
