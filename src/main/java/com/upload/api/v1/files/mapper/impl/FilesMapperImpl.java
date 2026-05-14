package com.upload.api.v1.files.mapper.impl;

import com.upload.api.v1.files.mapper.FilesMapper;
import com.upload.api.v1.files.req.CreateMultiDocumentReq;
import com.upload.api.v1.files.req.CreateOneDocumentReq;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class FilesMapperImpl implements FilesMapper {
    private final ModelMapper modelMapper;

    @Override
    public CreateOneDocumentReq mapCreateOneDocumentReq(CreateMultiDocumentReq req, MultipartFile file) {
        CreateOneDocumentReq createResp = new CreateOneDocumentReq();
        createResp.setDocuments(file);
        createResp.setDocumentClass(req.getDocumentClass());
        createResp.setOcr(req.getOcr());
        createResp.setFileDraft(req.getFileDraft());
        createResp.setFolderPath(req.getFolderPath());
        createResp.setProperties(req.getProperties());
        return createResp;
    }
}
