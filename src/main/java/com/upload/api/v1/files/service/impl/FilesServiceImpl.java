package com.upload.api.v1.files.service.impl;

import com.upload.api.v1.files.req.CreateFolderReq;
import com.upload.api.v1.files.req.CreateMultiDocumentReq;
import com.upload.api.v1.files.req.CreateOneDocumentReq;
import com.upload.api.v1.files.resp.CreateFileResp;
import com.upload.api.v1.files.service.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FilesServiceImpl implements FilesService {

    @Override
    public CreateFileResp upload(CreateOneDocumentReq req) {
        return null;
    }

    @Override
    public List<CreateFileResp> uploads(CreateMultiDocumentReq req) {
        return List.of();
    }

    @Override
    public Boolean createFolder(CreateFolderReq req) {
        return null;
    }
}
