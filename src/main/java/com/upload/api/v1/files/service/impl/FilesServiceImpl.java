package com.upload.api.v1.files.service.impl;

import com.upload.api.v1.ecm_audit_log.entity.EcmAuditLog;
import com.upload.api.v1.ecm_audit_log.repo.EcmAuditLogRepos;
import com.upload.api.v1.ecm_folder.entity.EcmFolder;
import com.upload.api.v1.ecm_folder.mapper.EcmFolderMapper;
import com.upload.api.v1.ecm_folder.repo.EcmFolderRepos;
import com.upload.api.v1.ecm_upload.entity.EcmUpload;
import com.upload.api.v1.ecm_upload.mapper.EcmUploadMapper;
import com.upload.api.v1.ecm_upload.repo.EcmUploadRepos;
import com.upload.api.v1.enums.AuditStatus;
import com.upload.api.v1.files.mapper.FilesMapper;
import com.upload.api.v1.files.req.CreateFolderReq;
import com.upload.api.v1.files.req.CreateMultiDocumentReq;
import com.upload.api.v1.files.req.CreateOneDocumentReq;
import com.upload.api.v1.files.resp.CreateFileResp;
import com.upload.api.v1.files.resp.SaveFileResp;
import com.upload.api.v1.files.service.FilesService;
import com.upload.utils.TraceContext;
import com.upload.constant.UploadConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilesServiceImpl implements FilesService {

    private final FilesMapper filesMapper;

    private final EcmUploadRepos ecmUploadRepos;
    private final EcmUploadMapper ecmUploadMapper;

    private final EcmFolderRepos ecmFolderRepos;
    private final EcmFolderMapper ecmFolderMapper;

    private final EcmAuditLogRepos ecmAuditLogRepos;

    @Override
    public CreateFileResp upload(CreateOneDocumentReq req) {
        return saveOneFile(req);
    }

    @Override
    public List<CreateFileResp> uploads(CreateMultiDocumentReq req) {
        MultipartFile[] files = req.getDocuments();
        if (files == null) return null;
        List<CreateFileResp> respAll = new ArrayList<>();
        for (MultipartFile file : files) {
            CreateOneDocumentReq createResp = filesMapper.mapCreateOneDocumentReq(req, file);
            CreateFileResp resp = saveOneFile(createResp);
            respAll.add(resp);
        }
        return respAll;
    }

    @Override
    public Boolean createFolder(CreateFolderReq req) {
        return null;
    }

    private CreateFileResp saveOneFile(CreateOneDocumentReq req) {
        String traceId = TraceContext.getTrace();
        String merchant = TraceContext.getMerchant();
        String url = "https://kimahome.vn";
        // Lưu thông tin file upload
        EcmUpload ecmUpload = ecmUploadMapper.mapReqToEcmUpload(req, traceId);
        ecmUpload.setUrl(url);
        ecmUploadRepos.save(ecmUpload);
        // Lưu thông tin folder
        saveEcmFolder(req.getFolderPath());
        // Lưu file
        SaveFileResp saveFileResp = save(req.getDocuments(), ecmUpload.getFolderPath(), ecmUpload.getDocument());
        CreateFileResp resp = new CreateFileResp();
        resp.setFileName(saveFileResp.getFileName());
        resp.setFolderPath(saveFileResp.getFolderPath());
        resp.setFileId(ecmUpload.getId());
        resp.setFileUrl(ecmUpload.getUrl());
        return resp;
    }

    private EcmFolder saveEcmFolder(String folderPath) {
        if(folderPath == null || folderPath.isBlank()) {
            LocalDate now = LocalDate.now();
            folderPath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        }
        EcmFolder ecmFolder = ecmFolderMapper.mapEcmUploadToEcmFolder(folderPath);
        if (createFolder(folderPath)) {
            ecmFolderRepos.save(ecmFolder);
        } else {
            ecmFolder.setIsExist(UploadConstant.IS_NOT_EXIST);
            ecmFolderRepos.save(ecmFolder);
        }
        return ecmFolder;
    }

    private boolean createFolder(String folderPath) {
        if (folderPath == null || folderPath.isBlank()) {
            return false;
        }
        try {
            File directory = new File(folderPath.trim());
            // Đã tồn tại
            if (directory.exists()) {
                return true;
            }
            // Tạo nhiều cấp folder
            return directory.mkdirs();
        } catch (Exception ex) {
            ex.printStackTrace();
            logError("Upload","createFolder", "ERROR", ex.getMessage());
            return false;
        }
    }

    public SaveFileResp save(MultipartFile file, String folder, String fileName) {
        SaveFileResp saveFileResp = new SaveFileResp();
        try {
            // Validate
            if (file == null || file.isEmpty()) {
                throw new Exception("File is empty");
            }

            // Chống path traversal
            String cleanFileName = Paths.get(fileName)
                    .getFileName()
                    .toString();

            // Folder upload
            Path uploadPath = Paths.get(folder);

            // Tạo folder nếu chưa có
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Full path file
            Path filePath = uploadPath.resolve(cleanFileName);

            // Save file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            // set permission
            filePath.toFile().setReadable(true, false);
            saveFileResp.setFileName(fileName);
            saveFileResp.setFolderPath(folder);
            // Return path
            return saveFileResp;
        } catch (Exception ex) {
            ex.printStackTrace();
            logError("Upload","save", "ERROR", ex.getMessage());
        }
        return saveFileResp;
    }

    public void logError(String urlApi,
                         String action,
                         String errorCode,
                         String errorMessage) {
        String traceId = TraceContext.getTrace();
        EcmAuditLog log = EcmAuditLog.builder()
                .urlApi(urlApi)
                .traceId(traceId)
                .action(action)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .retryCount(0)
                .status(AuditStatus.NEW)
                .lastRetryAt(null)
                .build();

        ecmAuditLogRepos.save(log);
    }

}


