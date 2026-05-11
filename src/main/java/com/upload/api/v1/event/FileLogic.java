//package com.upload.api.v1.event;
//
//import co.vn.api.entity.Images;
//import co.vn.api.mapper.ImagesMapper;
//import co.vn.api.model.admin.contract.ContractDto;
//import co.vn.api.model.admin.excel.MoneyHouseExcel;
//import co.vn.api.model.admin.file.UploadRoomImageDto;
//import co.vn.api.model.admin.images.ImagesResDto;
//import co.vn.api.model.admin.imp.ImportBase64;
//import co.vn.api.model.admin.room.RoomDto;
//import co.vn.api.model.admin.services.ServicesDto;
//import co.vn.api.model.admin.user.UserListDto;
//import co.vn.api.repository.ImagesRepository;
//import co.vn.api.util.AppContants;
//import co.vn.api.util.AppError;
//import co.vn.api.util.AppUUID;
//import co.vn.api.util.AppUtils;
//import co.vn.api.util.common.redis.RedisServices;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.poi.ss.usermodel.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class FileLogic {
//
//    @Autowired
//    private ImagesRepository imagesRepository;
//
//    @Autowired
//    private ImagesMapper imagesMapper;
//
//    @Value("${spring.folder.uploads}")
//    private String folders;
//
//    @Value("${client.url}")
//    private String httpUrl;
//
//    @Autowired
//    private UserLogic userLogic;
//
//    @Autowired
//    private RedisServices redisServices;
//
//    public void importDataExcelBase64(ImportBase64 importBase64) {
//
//    }
//
//    private RoomDto setRoomDto(MoneyHouseExcel moneyHouseExcel, Timestamp date) throws ParseException {
//        RoomDto roomDto = new RoomDto();
//        roomDto.setCodeRoom(moneyHouseExcel.getCodeRoom());
//        roomDto.setDescription(moneyHouseExcel.getDescription());
//        return roomDto;
//    }
//
//    private ServicesDto setServicesDto(MoneyHouseExcel moneyHouseExcel) throws ParseException {
//        ServicesDto dto = new ServicesDto();
//        dto.setCreateDate(AppUtils.getDateNowTimestamp());
//        return dto;
//    }
//
//    private ContractDto setContractsDto(MoneyHouseExcel moneyHouseExcel) throws ParseException {
//        ContractDto dto = new ContractDto();
//        dto.setCode(moneyHouseExcel.getCodeContract());
//        dto.setEndDate(AppUtils.parseDDMMYYYToTimestamp(moneyHouseExcel.getDateMonthEnd()));
//        dto.setStartDate(AppUtils.parseDDMMYYYToTimestamp(moneyHouseExcel.getDateContract()));
//        dto.setCreateDate(AppUtils.getDateNowTimestamp());
//        return dto;
//    }
//
//    /**
//     * Function read file
//     */
//    private List<MoneyHouseExcel> readExcelFile(Workbook workbook, Sheet sheet) {
//        List<MoneyHouseExcel> productList = new ArrayList<>();
//        String strDataExcel = "";
//        int rowIndex = 0;
//        int space = 0;
//        for (Row row : sheet) {
//            int index = 1;
//            MoneyHouseExcel product = new MoneyHouseExcel();
//            for (Cell cell : row) {
//                // CellReference cellRef = new CellReference(row.getRowNum(),
//                // cell.getColumnIndex());
//                // cellRef.formatAsString()
//                DataFormatter objDefaultFormat = new DataFormatter();
//                FormulaEvaluator objFormulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
//                DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                switch (cell.getCellType()) {
//                    case STRING:
//                        strDataExcel = cell.getRichStringCellValue().getString();
//                        break;
//                    case NUMERIC:
//                        if (DateUtil.isCellDateFormatted(cell)) {
//                            strDataExcel = cell.getLocalDateTimeCellValue().format(dateformat);
//                        } else {
//                            strDataExcel = cell.getNumericCellValue() + "";
//                        }
//                        break;
//                    case BOOLEAN:
//                        strDataExcel = cell.getBooleanCellValue() + "";
//                        break;
//                    case FORMULA:
////						strDataExcel = cell.getCellFormula();
//                        objFormulaEvaluator.evaluate(cell);
//                        strDataExcel = objDefaultFormat.formatCellValue(cell, objFormulaEvaluator);
//                        break;
//                    case BLANK:
//                        strDataExcel = "";
//                        break;
//                    default:
//                        strDataExcel = "";
//                }
//                if (index == 2) {
//                    product.setCodeRoom(AppUtils.randomOpt(6));
//                    if (strDataExcel != "") {
//                        product.setName(strDataExcel);
//                    }
//                } else if (index == 3) {
//                    if (strDataExcel != "") {
//                        product.setMoneyMonth(strDataExcel);
//                    }
//                } else if (index == 4) {
//                    product.setCodeContract(AppUtils.randomOpt(12));
//                    if (strDataExcel != "") {
//                        product.setDateMonthEnd(strDataExcel);
//                    }
//                } else if (index == 5) {
//                    if (strDataExcel != "") {
//                        product.setDateContract(strDataExcel);
//                    }
//                } else if (index == 6) {
//                    if (strDataExcel != "") {
//                        product.setDatePayStartEnd(strDataExcel);
//                    }
//                } else if (index == 7) {
//                    if (strDataExcel != "") {
//                        product.setDateSum(strDataExcel);
//                    }
//                } else if (index == 8) {
//                    if (strDataExcel != "") {
//                        product.setDeposit(strDataExcel);
//                    }
//                } else if (index == 9) {
//                    if (strDataExcel != "") {
//                        product.setMoneyRoom(strDataExcel);
//                    }
//                } else if (index == 10) {
//                    if (strDataExcel != "") {
//                        product.setSumMoney(strDataExcel);
//                    }
//                } else if (index == 11) {
//                    if (strDataExcel != "") {
//                        product.setGetMoneyDate(strDataExcel);
//                    }
//                } else if (index == 12) {
//                    if (strDataExcel != "") {
//                        product.setPeople(strDataExcel);
//                    }
//                } else if (index == 13) {
//                    if (strDataExcel != "") {
//                        product.setWater(strDataExcel);
//                    }
//                } else if (index == 14) {
//                    if (strDataExcel != "") {
//                        product.setWaterMoney(strDataExcel);
//                    }
//                } else if (index == 15) {
//                    if (strDataExcel != "") {
//                        product.setEleMoney(strDataExcel);
//                    }
//                } else if (index == 16) {
//                    if (strDataExcel != "") {
//                        product.setEnvOld(strDataExcel);
//                    }
//                } else if (index == 17) {
//                    if (strDataExcel != "") {
//                        product.setEnvNew(strDataExcel);
//                    }
//                } else if (index == 18) {
//                    if (strDataExcel != "") {
//                        product.setEnvUse(strDataExcel);
//                    }
//                } else if (index == 19) {
//                    if (strDataExcel != "") {
//                        product.setEnvMoney(strDataExcel);
//                    }
//                } else if (index == 20) {
//                    if (strDataExcel != "") {
//                        product.setCab(strDataExcel);
//                    }
//                } else if (index == 21) {
//                    if (strDataExcel != "") {
//                        product.setNet(strDataExcel);
//                    }
//                } else if (index == 22) {
//                    if (strDataExcel != "") {
//                        product.setSumServicesMoney(strDataExcel);
//                    }
//                } else if (index == 23) {
//                    if (strDataExcel != "") {
//                        product.setDebtLastMonth(strDataExcel);
//                    }
//                } else if (index == 24) {
//                    if (strDataExcel != "") {
//                        product.setTotalsMoney(strDataExcel);
//                    }
//                } else if (index == 25) {
//                    if (strDataExcel != "") {
//                        product.setTotalsMoneyHas(strDataExcel);
//                    }
//                } else if (index == 26) {
//                    if (strDataExcel != "") {
//                        product.setTotalDebtHas(strDataExcel);
//                    }
//                } else if (index == 27) {
//                    if (strDataExcel != "") {
//                        product.setDescription(strDataExcel);
//                    }
//                }
//
//                index++;
//            }
//            if (null != product) {
//                productList.add(product);
//                if (productList.size() > 60) {
//                    return productList;
//                }
//            } else {
//                space++;
//            }
//            if (space > 20) {
//                return productList;
//            }
//        }
//        return productList;
//
//    }
//
//    /**
//     * Decode base64
//     *
//     * @param encodedText
//     * @return
//     */
//    private byte[] base64Decode(String encodedText) {
//        try {
//            if (Base64.isBase64(encodedText)) {
//                return Base64.decodeBase64(encodedText);
//            } else {
//                System.out.println("$encodedText Not Base64 encoded String");
////                this.logger.info("$encodedText Not Base64 encoded String");
//                throw new Exception("Not Base64 encoded String");
//            }
//        } catch (Exception e) {
////            this.logger.info(e.getMessage());
//            System.out.println(e.getMessage());
//        }
//
//        return null;
//    }
//
//    public ImagesResDto uploadImagesAvartar(MultipartFile files) throws Exception {
//        UserListDto userListDto = AppUtils.redisUserListDto();
//        Images images = new Images();
//        String fileType = AppUtils.getFileImg(files.getOriginalFilename());
//        String fileName = AppUtils.getFileNameImg(files.getOriginalFilename()).concat(AppUtils.parseDateNowTimestamp().concat(fileType)).replaceAll(" ", "-");
//        images.setFileAuto(fileName);
//        images.setUrl(httpUrl);
//        images.setFolder(save(files, userListDto.getUserSecret(), fileName));
//        images.setFileSize(String.valueOf(files.getSize()));
//        images.setUserId(userListDto.getId());
//        images.setServices(userListDto.getUserName());
//        images.setFileType(AppUtils.getFileType(files.getOriginalFilename()));
//        images.setNameOld(files.getOriginalFilename());
//        images.setStatus(AppContants.NUMBER_2);
//        Images imagesInsert = this.imagesRepository.save(images);
//        if (null != imagesInsert) {
//            ImagesResDto imagesRes = new ImagesResDto();
//            imagesRes.setId(images.getId());
//            imagesRes.setFileName(images.getFileAuto());
//            imagesRes.setUrl(images.getUrl().concat(AppContants.STRING_SOURCE + images.getFolder().concat(AppContants.STRING_SOURCE + images.getFileAuto())));
//            imagesRes.setFileType(images.getFileType());
//            imagesRes.setCreateDate(images.getCreateDate());
//            this.userLogic.updateUrl(userListDto.getId(), imagesRes.getUrl());
//            userListDto.setUrlAvatar(imagesRes.getUrl());
//            this.redisServices.putUserInform(userListDto.getSessionId(), userListDto.getUserName(), userListDto, AppContants.TIME_SET_USER_LIST);
//            return imagesRes;
//        } else {
//            throw new Exception(AppError.MSGE0946);
//        }
//    }
//
//    public List<ImagesResDto> uploadRoomImages(UploadRoomImageDto uploadRoomImageDto) throws Exception {
//
//        List<ImagesResDto> uploadList = this.uploadImages(uploadRoomImageDto.getFiles());
//
//        return uploadList;
//    }
//
//    public List<ImagesResDto> uploadImages(MultipartFile[] file) throws Exception {
//        UserListDto userListDto = AppUtils.redisUserListDto();
//        String folder = userListDto.getCallId();
//        if (null == folder || folder == AppContants.STRING_SPACE) {
//            folder = userListDto.getUserName();
//        }
//        List<Images> imagesUpload = new ArrayList<>();
//        for (int i = 0; i < file.length; i++) {
//            Images images = new Images();
//            String fileName = AppUUID.getUUID().concat(AppUtils.getFileImg(file[i].getOriginalFilename()));
//            images.setFileAuto(fileName);
//            images.setUrl(httpUrl);
//            images.setFolder(save(file[i], folder, fileName));
//            images.setFileSize(String.valueOf(file[i].getSize()));
//            images.setUserId(userListDto.getId());
//            images.setServices(userListDto.getUserName());
//            images.setStatus(AppContants.NUMBER_1);
//            images.setFileType(AppUtils.getFileType(file[i].getOriginalFilename()));
//            images.setNameOld(file[i].getOriginalFilename());
//            imagesUpload.add(images);
//        }
//
//        List<Images> imagesResult = this.imagesRepository.saveAll(imagesUpload);
//        if (imagesResult.size() == 0) {
//            throw new Exception(AppError.MSGE0966);
//        }
//        List<ImagesResDto> imagesList = new ArrayList<>();
//        for (Images images : imagesResult) {
//            ImagesResDto imagesResDto = new ImagesResDto();
//            imagesResDto.setId(images.getId());
//            imagesResDto.setFileName(images.getFileAuto());
//            imagesResDto.setUrl(images.getUrl().concat(AppContants.STRING_SOURCE + images.getFolder().concat(AppContants.STRING_SOURCE + images.getFileAuto())));
//            imagesResDto.setCreateDate(images.getCreateDate());
//            imagesResDto.setFileType(images.getFileType());
//            imagesList.add(imagesResDto);
//        }
//        return imagesList;
//    }
//
//    public String save(MultipartFile file, String folder, String fileName) throws Exception {
//        try {
//            File directoryUpload = new File(this.folders.toString());
//            if (!directoryUpload.exists()) {
//                directoryUpload.mkdir();
//            }
//
//            File directoryUser = new File(this.folders.concat(AppContants.STRING_SOURCE + folder));
//            if (!directoryUser.exists()) {
//                directoryUser.mkdir();
//            }
//
//            String month = AppUtils.getYYYYMM();
//            Path root = Paths.get(this.folders.concat(AppContants.STRING_SOURCE + folder.concat(AppContants.STRING_SOURCE + month)));
//            File directoryRoot = new File(this.folders.concat(AppContants.STRING_SOURCE + folder.concat(AppContants.STRING_SOURCE + month)));
//            if (!directoryRoot.exists()) {
//                directoryRoot.mkdir();
//            }
//            Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//            return "uploads" + "/" + folder + "/" + month;
//        } catch (Exception ex) {
//            throw new Exception(AppError.MSGE0967);
//        }
//    }
//
////
////    public String save(MultipartFile file, String folder, String fileName) throws Exception {
////        try {
////
////            Path upload = Paths.get(this.folders);
////            System.out.println(fileName);
////            System.out.println("upload");
////            System.out.println(upload);
////            if (Files.notExists(upload)) {
////                Files.createDirectory(upload);
////            }
////
////            Path user = Paths.get(this.folders.concat(AppContants.STRING_SOURCE+folder));
////            System.out.println("user");
////            System.out.println(user);
////            if (Files.notExists(user)) {
////                Files.createDirectory(user);
////            }
////
////            String month = AppUtils.getYYYYMM();
////            Path root = Paths.get(this.folders.concat(AppContants.STRING_SOURCE+folder.concat(AppContants.STRING_SOURCE+month)));
////            System.out.println("root");
////            System.out.println(root);
////            if (Files.notExists(root)) {
////                Files.createDirectory(root);
////            }
////
////            Files.copy(file.getInputStream(), root.resolve(fileName));
////            return folder + "/" + month;
////        } catch (Exception e) {
////            throw new Exception(AppError.MSGE0967);
////        }
////    }
//}
