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
//
//package com.vietcombank.tf.integration.service.impl;
//
//import com.vietcombank.tf.integration.config.TraceContext;
//import com.vietcombank.tf.integration.constants.FilenetConstants;
//import com.vietcombank.tf.integration.constants.UploadConstants;
//import com.vietcombank.tf.integration.dto.FileInformDto;
//import com.vietcombank.tf.integration.dto.request.CreateMultiDocumentReq;
//import com.vietcombank.tf.integration.dto.request.CreateOneDocumentReq;
//import com.vietcombank.tf.integration.dto.request.FolderCheckReq;
//import com.vietcombank.tf.integration.dto.request.FolderCreateReq;
//import com.vietcombank.tf.integration.dto.response.CreateOneDocumentResp;
//import com.vietcombank.tf.integration.dto.response.DeleteDocumentResp;
//import com.vietcombank.tf.integration.dto.response.FolderCheckResp;
//import com.vietcombank.tf.integration.dto.response.FolderCreateResp;
//import com.vietcombank.tf.integration.entity.*;
//        import com.vietcombank.tf.integration.enums.*;
//        import com.vietcombank.tf.integration.filenet.FilenetClient;
//import com.vietcombank.tf.integration.mappers.EcmUploadMapper;
//import com.vietcombank.tf.integration.repository.*;
//        import com.vietcombank.tf.integration.service.EcmUploadService;
//import com.vietcombank.tf.integration.utils.ECMUtils;
//import com.vietcombank.tf.integration.utils.FilenetUtils;
//import com.vietcombank.tf.integration.utils.GLIMUtils;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.regex.Pattern;
//
//@Service
//@RequiredArgsConstructor
//public class EcmUploadServiceImpl implements EcmUploadService {
//
//    private final EcmUploadRepository ecmUploadRepository;
//    private final EcmUploadChunkRepository ecmUploadChunkRepository;
//    private final EcmFolderRepository ecmFolderRepository;
//    private final FilenetClient filenetClient;
//    private final EcmDocumentMetadataRepository ecmDocumentMetadataRepository;
//    private final EcmDocumentRepository ecmDocumentRepository;
//    private final EcmBlTempRepository ecmBlTempRepository;
//    private final EcmDocumentDeleteRepository ecmDocumentDeleteRepository;
//
//    @Value("${filenet.rootFolderPath}")
//    private String ROOT_FOLDER_PATH;
//    @Value("${filenet.objectStore}")
//    private String OBJECT_STORE;
//    @Value("${filenet.url}")
//    private String FILENET_BASE_URL;
//
//    private Integer TEMP = 1;
//    private Integer UPLOAD = 60;
//
//    @Override
//    @Transactional
//    public CreateOneDocumentResp upload(CreateOneDocumentReq req) {
//        String traceId = TraceContext.get();
//        return saveOneFile(req, traceId);
//    }
//
//    @Override
//    @Transactional
//    public List<CreateOneDocumentResp> uploads(CreateMultiDocumentReq multiReq) {
//        MultipartFile[] files = multiReq.getDocuments();
//        String traceId = TraceContext.get();
//        if (files == null) return null;
//        List<CreateOneDocumentResp> respAll = new ArrayList<>();
//        for (MultipartFile file : files) {
//            CreateOneDocumentReq req = new CreateOneDocumentReq();
//            req.setDocuments(file);
//            req.setDocumentClass(multiReq.getDocumentClass());
//            req.setOcr(multiReq.getOcr());
//            req.setFileDraft(multiReq.getFileDraft());
//            req.setFolderPath(multiReq.getFolderPath());
//            req.setProperties(multiReq.getProperties());
//            CreateOneDocumentResp resp = saveOneFile(req, traceId);
//            respAll.add(resp);
//        }
//        return respAll;
//    }
//
//
//    @Override
//    public FolderCheckResp getFolderIdByFolderPath(FolderCheckReq req) {
//        if (req.getObjectStore() == null || req.getObjectStore().isEmpty()) {
//            req.setObjectStore(OBJECT_STORE);
//        }
//        FolderCheckResp resp = new FolderCheckResp();
//        String folderPathSub = req.getRootFolderPath().replaceFirst("^" + Pattern.quote(ROOT_FOLDER_PATH), "");
//        Optional<EcmFolder> ecmFolderOpt = ecmFolderRepository.findTopByFolderPathSubAndStatus(folderPathSub, FolderStatus.CHECKED);
//        if (ecmFolderOpt.isPresent()) {
//            EcmFolder ecmFolder = ecmFolderOpt.get();
//            if (ecmFolder.getIsExist() == 1) {
//                resp = setFolderCheck(ecmFolderOpt.get());
//            }
//        } else {
//            resp = filenetClient.getFolderIdByFolderPath(req);
//        }
//        return resp;
//    }
//
//    private FolderCheckResp setFolderCheck(EcmFolder ecmFolder) {
//        FolderCheckResp resp = new FolderCheckResp();
//        resp.setResponseCode("105");
//        resp.setResponseMessage("Folder existed.");
//        resp.setFolderId(ecmFolder.getFolderId());
//        return resp;
//    }
//
//    @Override
//    public FolderCreateResp createFolder(FolderCreateReq req) {
//
//        FolderCreateResp resp = new FolderCreateResp();
//        Optional<EcmFolder> ecmFolderOpt = ecmFolderRepository.findTopByFolderPathSubAndStatus(req.getCreateFolderName(), FolderStatus.CHECKED);
//        if (ecmFolderOpt.isPresent()) {
//            resp = getFolderCreateResp(ecmFolderOpt.get());
//        } else {
//            String folder = req.getCreateFolderName();
//            boolean hasSlash = folder.contains("/");
//            if (hasSlash) {
//                resp = filenetClient.createSubFolderPath(req);
//            } else {
//                resp = filenetClient.createFolder(req);
//            }
//            return updateEcmFolderIfExist(resp, req);
//        }
//        return resp;
//    }
//
//    @Override
//    public FolderCreateResp createSubFolderPath(FolderCreateReq req) {
//
//        FolderCreateResp resp = new FolderCreateResp();
//        Optional<EcmFolder> ecmFolderOpt = ecmFolderRepository.findTopByFolderPathSubAndStatus(req.getCreateFolderName(), FolderStatus.CHECKED);
//        if (ecmFolderOpt.isPresent()) {
//            resp = getFolderCreateResp(ecmFolderOpt.get());
//        } else {
//            resp = filenetClient.createSubFolderPath(req);
//            return updateEcmFolderIfExist(resp, req);
//        }
//        return resp;
//    }
//
//    @Override
//    public ByteArrayResource downloadMultiDocument(String folderPath, String objectStore) {
//        if (objectStore == null || objectStore.isEmpty()) {
//            objectStore = OBJECT_STORE;
//        }
//        String urlAction = FILENET_BASE_URL + FilenetConstants.DOWNLOAD_MULTI_DOCUMENT;
//        String url = UriComponentsBuilder
//                .fromHttpUrl(urlAction)
//                .queryParam("folderPath", folderPath)
//                .queryParam("objectStore", objectStore)
//                .build()
//                .toUriString();
//        try {
//            String action = ActionCallApi.DOWNLOAD_MULTI_DOCUMENT.toString();
//            return filenetClient.downloadMultiDocumentStream(url, urlAction, action);
//        } catch (Exception e) {
//            throw new RuntimeException("Folder does not exist.");
//        }
//
//    }
//
//    @Override
//    public ByteArrayResource downloadOneDocument(String documentId, String objectStore) {
//        if (objectStore == null || objectStore.isEmpty()) {
//            objectStore = OBJECT_STORE;
//        }
//        String url = UriComponentsBuilder
//                .fromHttpUrl(FILENET_BASE_URL + FilenetConstants.DOWNLOAD_DOCUMENT)
//                .queryParam("documentId", documentId)
//                .queryParam("objectStore", objectStore)
//                .build()
//                .toUriString();
//        String urlAction = FILENET_BASE_URL + FilenetConstants.DOWNLOAD_DOCUMENT;
//        String action = ActionCallApi.DOWNLOAD_ONE_DOCUMENT.toString();
//        try {
//            return filenetClient.downloadOneDocument(url, urlAction, action);
//        } catch (Exception e) {
//            throw new RuntimeException("Folder does not exist.");
//        }
//
//    }
//
//    @Override
//    public DeleteDocumentResp deletedocument(String documentId, String objectStore) {
//        DeleteDocumentResp resp = new DeleteDocumentResp();
//        if (objectStore == null || objectStore.isEmpty()) {
//            Optional<EcmDocument> documentIdOpt = ecmDocumentRepository.findTopByDocumentId(documentId);
//            if (documentIdOpt.isEmpty()) {
//                resp.setResponseCode("102");
//                resp.setResponseMessage("Document is empty");
//            }
//            objectStore = documentIdOpt.get().getObjectStore();
//        }
//        Optional<EcmDocument> ecmDocumentOpt = ecmDocumentRepository.findTopByDocumentIdAndObjectStore(documentId, objectStore);
//        if (ecmDocumentOpt.isEmpty()) {
//            resp.setResponseCode("102");
//            resp.setResponseMessage("Document is empty");
//        } else {
//            EcmDocument ecmDocument = ecmDocumentOpt.get();
//            ecmDocument.setStatus(DocumentStatus.DELETED);
//            ecmDocumentRepository.save(ecmDocument);
//
//            Optional<EcmBlTemp> ecmBlTempOpt = ecmBlTempRepository.findTopByObjectId(ecmDocument.getDocumentId());
//            if (ecmBlTempOpt.isPresent()) {
//                EcmBlTemp ecmBlTemp = ecmBlTempOpt.get();
//                ecmBlTemp.setStatus(TempStatus.TEMP);
//                ecmBlTemp.setIsScan(false);
//                ecmBlTempRepository.save(ecmBlTemp);
//                resp.setResponseCode("0");
//                resp.setResponseMessage("Success");
//            } else {
//                resp.setResponseCode("102");
//                resp.setResponseMessage("Document is empty");
//            }
//
//        }
//        return resp;
//    }
//
//    @Override
//    public void deleteAll() {
//// 1) Lấy danh sách Temp delete
//        List<EcmBlTemp> tempDelete = ecmBlTempRepository.findAllByIsScanFalseOrIsScanIsNull();
//        // 2) Xử lý
//        if (!tempDelete.isEmpty()) {
//            List<EcmBlTemp> tempUpdate = new ArrayList<>();
//            List<EcmDocumentDelete> deleteList = new ArrayList<>();
//            for (EcmBlTemp ecmBlTemp : tempDelete) {
//                EcmDocumentDelete ecmDocumentDelete = new EcmDocumentDelete();
//                ecmDocumentDelete.setObjectStore(ecmBlTemp.getObjectStore());
//                ecmDocumentDelete.setDocumentId(ecmBlTemp.getObjectId());
//                if (ecmBlTemp.getStatus() == TempStatus.TEMP) {
//                    ecmDocumentDelete.setDateNumber(TEMP);
//                    LocalDateTime dateDele = ECMUtils.toEndOfDay23(ecmBlTemp.getCreatedAt());
//                    ecmDocumentDelete.setDateDelete(dateDele);
//                } else {
//                    ecmDocumentDelete.setDateNumber(UPLOAD);
//                    LocalDateTime dateDele = ecmBlTemp.getCreatedAt().plusDays(UPLOAD);
//                    ecmDocumentDelete.setDateDelete(dateDele);
//                }
//                ecmDocumentDelete.setCreatedBy("processDeleteFile");
//                ecmDocumentDelete.setRetry(0);
//                ecmDocumentDelete.setStatus(ErrorLogStatus.NEW.toString());
//
//                deleteList.add(ecmDocumentDelete);
//
//                ecmBlTemp.setIsScan(true);
//                tempUpdate.add(ecmBlTemp);
//            }
//            ecmDocumentDeleteRepository.saveAll(deleteList);
//            ecmBlTempRepository.saveAll(tempUpdate);
//        }
//    }
//
//    @Override
//    @Transactional
//    public void deleteByDocumentId(LocalDateTime start, LocalDateTime end) {
//        List<EcmDocumentDelete> items = ecmDocumentDeleteRepository.findDeleted(start, end);
//        if (items.isEmpty()) return;
//        for (EcmDocumentDelete d : items) {
//            deleteOne(d, "system-job");
//        }
//    }
//
//    private void deleteOne(EcmDocumentDelete d, String deletedBy) {
//        boolean result = filenetClient.deleteFileToECM(d.getDocumentId(), d.getObjectStore());
//        if (result) {
//            d.setStatus("SUCCESS");
//        } else {
//            d.setStatus("FALSE");
//        }
//        d.setDeletedBy(deletedBy);
//        d.setRetry(setRetry(d.getRetry()));
//        d.setDeletedAt(LocalDateTime.now());
//        ecmDocumentDeleteRepository.saveAndFlush(d);
//    }
//
//    private Integer setRetry(Integer retry) {
//        if (retry == null) return 0;
//        return retry + 1;
//    }
//
//
//    private FolderCreateResp getFolderCreateResp(EcmFolder entity) {
//        FolderCreateResp resp = new FolderCreateResp();
//        String guid = FilenetUtils.extractGuid(entity.getFolderId());
//        resp.setResponseCode("105");
//        resp.setResponseMessage("Folder existed.");
//        resp.setGuid(guid);
//        resp.setFolderId(entity.getFolderId());
//        resp.setObjectId(entity.getFolderId());
//        return resp;
//    }
//
//    private FolderCreateResp updateEcmFolderIfExist(FolderCreateResp resp, FolderCreateReq req) {
//        int status = Integer.parseInt(resp.getResponseCode());
//        if (status == 105) {
//            Optional<EcmFolder> ecmFolderOpt = ecmFolderRepository.findTopByFolderPathSub(req.getCreateFolderName());
//            if (ecmFolderOpt.isPresent()) {
//                EcmFolder ecmFolder = ecmFolderOpt.get();
//                ecmFolder.setIsExist(1);
//                ecmFolder.setFolderId(resp.getFolderId());
//                ecmFolder.setStatus(FolderStatus.CHECKED);
//                resp.setObjectId(ecmFolder.getObjectId());
//                resp.setGuid(ecmFolder.getGuId());
//                ecmFolderRepository.save(ecmFolder);
//            }
//
//        } else {
//            EcmFolder ecmFolder = new EcmFolder();
//            ecmFolder.setFolderPath(ROOT_FOLDER_PATH);
//            ecmFolder.setFolderPathSub(req.getCreateFolderName());
//            ecmFolder.setIsExist(0);
//            ecmFolder.setFolderId(resp.getFolderId());
//            ecmFolder.setObjectId(resp.getObjectId());
//            ecmFolder.setGuId(resp.getGuid());
//            ecmFolder.setStatus(FolderStatus.INIT);
//            ecmFolderRepository.save(ecmFolder);
//        }
//        return resp;
//    }
//
//    private CreateOneDocumentResp saveOneFile(CreateOneDocumentReq req, String traceId) {
//        // Lưu thông tin file upload
//        EcmUpload ecmUpload = EcmUploadMapper.toEntity(req);
//        ecmUpload.setTraceId(traceId);
//        ecmUpload.setObjectStore(OBJECT_STORE);
//        EcmUpload saveUpload = ecmUploadRepository.save(ecmUpload);
//        // Lưu thông tin folder
//        saveEcmFolder(saveUpload);
//        CreateOneDocumentResp resp = filenetClient.sendFileToECM(req);
//        resp.setFileName(saveUpload.getDocument());
//        EcmDocument ecmDocument = saveEcmDocument(ecmUpload, resp);
//        saveEcmBlTemp(ecmUpload, ecmDocument);
//        return resp;
//    }
//
//    private CreateOneDocumentResp saveOneFileChunk(CreateOneDocumentReq req, String traceId) {
//        // Lưu thông tin file upload
//        EcmUpload ecmUpload = EcmUploadMapper.toEntity(req);
//        ecmUpload.setTraceId(traceId);
//        EcmUpload saveUpload = ecmUploadRepository.save(ecmUpload);
//        // 3) Tạo chunk 5MB theo streaming
//        boolean chunkFile = createChunkFile(req.getDocuments(), saveUpload);
//
//        // 6) Sau khi tách chunk xong:
//        //    - Có thể kick job/background để gửi tất cả PENDING chunks lên ECM
//        //    - Hoặc nếu đã gửi ở bước trên, cập nhật trạng thái:
//        if (chunkFile) {
//            saveUpload.setUploadStatus(UploadStatus.INIT);
//        } else {
//            // Nếu lỗi IO: cập nhật trạng thái upload -> FAILED và return false
//            saveUpload.setUploadStatus(UploadStatus.FAILED);
//        }
//        ecmUploadRepository.save(saveUpload);
//        // Lưu thông tin folder
//        saveEcmFolder(saveUpload);
//
//        CreateOneDocumentResp resp = new CreateOneDocumentResp();
//        resp.setObjectId("{F0EE798F-0000-C01A-8CD5-B83C81452AD8}");
//        resp.setFileName(saveUpload.getDocument());
//        resp.setResponseMessage("SUCCESS");
//        resp.setResponseCode("0");
//
//        return resp;
//    }
//
//    private void saveEcmFolder(EcmUpload ecmUpload) {
//        EcmFolder ecmFolder = new EcmFolder();
//        ecmFolder.setFolderPath(ecmUpload.getFolderPath());
//        ecmFolder.setUploadId(ecmUpload.getId());
//        ecmFolder.setIsExist(0);
//        ecmFolder.setStatus(FolderStatus.INIT);
//        ecmFolderRepository.save(ecmFolder);
//    }
//
//    private EcmDocument saveEcmDocument(EcmUpload ecmUpload, CreateOneDocumentResp resp) {
//        EcmDocument ecmDocument = new EcmDocument();
//        String documentId = FilenetUtils.extractGuid(resp.getObjectId());
//        ecmDocument.setDocumentId(documentId);
//        ecmDocument.setObjectStore(ecmUpload.getObjectStore());
//        ecmDocument.setStatus(DocumentStatus.ACTIVE);
//        ecmDocument.setUploadId(ecmUpload.getId());
//        return ecmDocumentRepository.save(ecmDocument);
//    }
//
//    private void saveEcmBlTemp(EcmUpload ecmUpload, EcmDocument ecmDocument) {
//        EcmBlTemp ecmBlTemp = new EcmBlTemp();
//        String ebankRef = GLIMUtils.generateEbankRef(EbankRef.BG.toString(), 1);
//        String transactionRef = GLIMUtils.generateTransactionRef(ebankRef, TransactionRef.ISS.toString(), 1);
//        ecmBlTemp.setEbankRef(ebankRef);
//        ecmBlTemp.setTransactionRef(transactionRef);
//        ecmBlTemp.setUploadId(ecmUpload.getId());
//        ecmBlTemp.setObjectId(ecmDocument.getDocumentId());
//        ecmBlTemp.setDocumentId(ecmDocument.getId());
//        ecmBlTemp.setObjectStore(ecmUpload.getObjectStore());
//        ecmBlTemp.setFolderPath(ecmUpload.getFolderPath());
//        ecmBlTemp.setStatus(TempStatus.UPLOAD);
//        ecmBlTemp.setIsScan(false);
//        ecmBlTempRepository.save(ecmBlTemp);
//    }
//
//    private FileInformDto getFileInform(MultipartFile file) {
//        FileInformDto fileDto = new FileInformDto();
//        // Tên file gốc
//        String fileName = file.getOriginalFilename();
//        // Mime type (content-type)
//        String mimeType = file.getContentType();
//
//        // Dung lượng file (bytes)
//        long fileSizeBytes = file.getSize();
//        fileDto.setFileName(fileName);
//        fileDto.setMimeType(mimeType);
//        fileDto.setFileSizeBytes(fileSizeBytes);
//        return fileDto;
//    }
//
//    private boolean createChunkFile(MultipartFile file, EcmUpload upload) {
//        final int chunkSize = UploadConstants.CHUNK_SIZE_BYTES; // 5MB
//        int index = 0;
//
//        try (InputStream in = file.getInputStream()) {
//
//            byte[] buffer = new byte[chunkSize];
//
//            while (true) {
//                int bytesReadTotal = 0;
//
//                while (bytesReadTotal < chunkSize) {
//                    int n = in.read(buffer, bytesReadTotal, chunkSize - bytesReadTotal);
//                    if (n == -1) break; // EOF
//                    bytesReadTotal += n;
//                }
//
//                if (bytesReadTotal == 0) break;
//
//                // lấy nội dung chunk đúng kích thước
//                byte[] chunkContent = Arrays.copyOf(buffer, bytesReadTotal);
//
//                // Lưu metadata chunk vào DB
//                EcmUploadChunk chunkResp = saveEcmUploadChunk(upload, index, bytesReadTotal);
//                saveEcmDocumentMetadata(chunkResp, chunkContent);
//                // Gửi chunk sang ECM/FileNet (nếu muốn gửi ngay)
//                boolean sent = filenetClient.sendChunkToECM(upload, index, chunkContent);
//                // if (sent) updateChunkStatus(chunkResp.getId(), "SENT");
//
//                // Lưu file chunk tạm vào đĩa
//                // saveChunkToDisk(upload.getId(), index, chunkContent);
//
//                index++;
//            }
//
//        } catch (IOException ex) {
//            return false;
//        }
//        return true;
//    }
//
//    private EcmUploadChunk saveEcmUploadChunk(EcmUpload upload, int index, int bytesReadTotal) {
//
//        EcmUploadChunk chunk = EcmUploadChunk.builder()
//                .documentId(upload.getId())
//                .uploadId(upload.getId())
//                .chunkIndex(index)
//                .chunkSizeMb((int) Math.ceil(bytesReadTotal / (1024.0 * 1024.0)))
//                .status(UploadChunkStatus.PENDING)
//                .build();
//        return ecmUploadChunkRepository.save(chunk);
//    }
//
//    private EcmDocumentMetadata saveEcmDocumentMetadata(EcmUploadChunk chunkResp, byte[] chunkContent) {
//        EcmDocumentMetadata chunk = EcmDocumentMetadata.builder()
//                .refId(chunkResp.getId())
//                .type(MetaType.FILE_TRUNK)
//                .metaValue(chunkContent)
//                .build();
//        return ecmDocumentMetadataRepository.save(chunk);
//    }
//
//
//}

