package com.upload.api.v1.files.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFileResp {
    private Long fileId;
    private String fileUrl;
    private String folderPath;

}
