package com.upload.api.v1.files.service.impl;

import com.upload.api.v1.ecm_audit_log.entity.EcmAuditLog;
import com.upload.api.v1.ecm_audit_log.repo.EcmAuditLogRepos;
import com.upload.api.v1.ecm_auth.entity.EcmAuth;
import com.upload.api.v1.ecm_auth.repo.EcmAuthRepos;
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
import com.vn.lib.iam.auth.AesKeyDto;
import com.vn.lib.utils.AesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.UUID;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class FilesServiceImpl implements FilesService {

    @Value("${spring.folder.uploads}")
    private String rootUpload;

    private final FilesMapper filesMapper;

    private final EcmUploadRepos ecmUploadRepos;
    private final EcmUploadMapper ecmUploadMapper;

    private final EcmFolderRepos ecmFolderRepos;
    private final EcmFolderMapper ecmFolderMapper;

    private final EcmAuditLogRepos ecmAuditLogRepos;
    private final EcmAuthRepos ecmAuthRepos;

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

        if (req.getOcr() == null) {
            req.setOcr("Y");
        }
        if (req.getFileDraft() == null) {
            req.setOcr("N");
        }
        if (req.getDocumentClass() == null) {
            req.setOcr("ALL");
        }
        CreateFileResp resp = new CreateFileResp();
        String traceId = TraceContext.getTrace();
        String merchant = TraceContext.getMerchant();
        AesKeyDto aesKeyDto = AesUtil.encryptDto(AesUtil.decrypt(merchant));
        EcmAuth ecmAuth = ecmAuthRepos.findTopByClientSecret(merchant);

        // Lưu thông tin folder
        EcmFolder ecmFolder = saveEcmFolder(req.getFolderPath(), aesKeyDto);

        // Lưu thông tin file upload
        EcmUpload ecmUpload = ecmUploadMapper.mapReqToEcmUpload(req, traceId, aesKeyDto);
        ecmUpload.setUrl(ecmAuth.getServerUrl());
        ecmUpload.setFolderPath(ecmFolder.getFolderPath());
        ecmUploadRepos.save(ecmUpload);
        resp.setFolderPath(ecmFolder.getFolderPath());
        // Lưu file
        String folder = rootUpload.concat(aesKeyDto.getServiceName().concat(ecmFolder.getFolderPath()));
        if (createFolder(folder)) {
            ecmFolderRepos.save(ecmFolder);
        } else {
            ecmFolder.setIsExist(UploadConstant.IS_NOT_EXIST);
            ecmFolderRepos.save(ecmFolder);
        }
        SaveFileResp saveFileResp = save(req.getDocuments(), folder, ecmUpload.getDocument());

        resp.setFileName(saveFileResp.getFileName());

        resp.setFileId(ecmUpload.getId());
        resp.setFileUrl(ecmUpload.getUrl());
        return resp;
    }

    private EcmFolder saveEcmFolder(String folderPath, AesKeyDto aesKeyDto) {
        if (folderPath == null || folderPath.isBlank()) {
            LocalDate now = LocalDate.now();
            folderPath = "/" + now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        }
        folderPath = folderPath.replaceAll("(?<!:)/{2,}", "/");
        return ecmFolderMapper.mapEcmUploadToEcmFolder(folderPath, aesKeyDto);
    }

    private boolean createFolder(String folderPath) {
        if (folderPath == null || folderPath.isBlank()) {
            return false;
        }
        try {
//            File directory = new File(folderPath.trim());
//            // Đã tồn tại
//            if (directory.exists()) {
//                return true;
//            }
            Path path = Paths.get(folderPath);
            Files.createDirectories(path);
            logError("Upload", "save", "SUCCESS", folderPath);
            // Tạo nhiều cấp folder
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            logError("Upload", "createFolder", "ERROR", ex.getMessage());
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
            validateFileName(cleanFileName);
            String extension = "";

            int lastDot = cleanFileName.lastIndexOf(".");

            if (lastDot > 0) {
                extension = cleanFileName.substring(lastDot);
            }

            String newFileName = UUID.randomUUID() + extension;

            // Folder upload
            Path uploadPath = Paths.get(folder);

            // Tạo folder nếu chưa có
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Full path file
            Path filePath = uploadPath.resolve(newFileName);

            // Save file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            // set permission
            filePath.toFile().setReadable(true, false);
            saveFileResp.setFileName(newFileName);
            saveFileResp.setFolderPath(folder);
            // Return path
            logError("Upload", "save", "SUCCESS", filePath.toString());
            return saveFileResp;
        } catch (Exception ex) {
            ex.printStackTrace();
            logError("Upload", "save", "ERROR", ex.getMessage());
        }
        return saveFileResp;
    }

    public void validateFileName(String fileName) throws Exception {

        if (fileName == null || fileName.isBlank()) {
            throw new Exception("File name is empty");
        }

        // remove path
        String cleanFileName = Paths.get(fileName)
                .getFileName()
                .toString();

        // Không cho phép nhiều dấu chấm
        long dotCount = cleanFileName.chars()
                .filter(ch -> ch == '.')
                .count();

        if (dotCount > 1) {
            throw new Exception("Invalid file name: multiple dots");
        }

        // Regex chỉ cho phép file có khoảng trắng
        Pattern pattern = Pattern.compile("^[\\p{L}0-9._\\-() ]+$");

        if (!pattern.matcher(cleanFileName).matches()) {
            throw new Exception("Invalid file name: special characters detected");
        }

        // Không cho phép bắt đầu bằng dấu chấm
        if (cleanFileName.startsWith(".")) {
            throw new Exception("Hidden file is not allowed");
        }

        // Không cho phép ..
        if (cleanFileName.contains("..")) {
            throw new Exception("Path traversal detected");
        }
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


