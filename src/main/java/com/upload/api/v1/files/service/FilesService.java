package com.upload.api.v1.files.service;

import com.upload.api.v1.files.req.CreateFolderReq;
import com.upload.api.v1.files.req.CreateMultiDocumentReq;
import com.upload.api.v1.files.req.CreateOneDocumentReq;
import com.upload.api.v1.files.resp.CreateFileResp;
import java.util.List;

public interface FilesService {

    CreateFileResp upload(CreateOneDocumentReq req);

    List<CreateFileResp> uploads(CreateMultiDocumentReq req);

    Boolean createFolder(CreateFolderReq req);

}
