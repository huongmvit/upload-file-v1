package com.upload.api.v1.files.mapper;

import com.upload.api.v1.files.req.CreateMultiDocumentReq;
import com.upload.api.v1.files.req.CreateOneDocumentReq;
import org.springframework.web.multipart.MultipartFile;

public interface FilesMapper  {
    CreateOneDocumentReq mapCreateOneDocumentReq(CreateMultiDocumentReq req, MultipartFile file);
}
