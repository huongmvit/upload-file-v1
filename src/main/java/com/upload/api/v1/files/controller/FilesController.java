package com.upload.api.v1.files.controller;

import com.upload.api.v1.files.req.CreateFolderReq;
import com.upload.api.v1.files.req.CreateMultiDocumentReq;
import com.upload.api.v1.files.req.CreateOneDocumentReq;
import com.upload.api.v1.files.service.FilesService;
import com.vn.lib.common.resp.ResponseApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class FilesController {
    private final FilesService filesService;

    /**
     * API tải một file lên ECM
     *
     * @param req
     * @return
     */

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseApi upload(
            @ModelAttribute @Valid CreateOneDocumentReq req) {
        return new ResponseApi(filesService.upload(req));
    }


    /**
     * API tải nhiều file lên ECM
     *
     * @param req
     * @return
     */
    @PostMapping(value = "/uploads",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseApi uploads(@ModelAttribute @Valid CreateMultiDocumentReq req) {
        return new ResponseApi(filesService.uploads(req));
    }

    /**
     * Tạo folder trên ECM
     *
     * @param req
     * @return
     */
    @PostMapping(value = "/createFolder", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseApi createFolder(@RequestBody @Valid CreateFolderReq req) {
        return new ResponseApi(filesService.createFolder(req));
    }

}
