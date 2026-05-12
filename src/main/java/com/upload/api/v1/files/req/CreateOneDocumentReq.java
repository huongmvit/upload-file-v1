package com.upload.api.v1.files.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOneDocumentReq {
    @JsonIgnore
    private MultipartFile documents;
    private List<PropertyReq> properties;
    private String ocr;
    private String fileDraft;
    private String folderPath;
    private String documentClass;
}
