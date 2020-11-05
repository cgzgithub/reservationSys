package com.ust.shbay.manager.api.controller;

import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.api.controller.dto.file.FileDownload;
import com.ust.shbay.manager.api.controller.dto.file.FileUpload;
import com.ust.shbay.manager.biz.file.FileBusiness;
import com.ust.shbay.service.file.bo.FileDownloadBO;
import com.ust.shbay.service.base.BaseController;
import com.ust.shbay.service.file.bo.FileUploadBO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 */
@Slf4j
@Api(tags = "文件服务")
@RestController
@RequestMapping("api/file/")
public class FileController extends BaseController {

    @Autowired
    private FileBusiness fileBusiness;

    @PostMapping("upload")
    public ResponseEntity upload(FileUpload fileUpload, @RequestParam("file") MultipartFile file) {
        log.debug("DEBUG: 上传文件");
        if (file.isEmpty()) {
            log.info("INFO: upload文件为空。");
            return ResponseEntity.buildFailedEntity("文件为空");
        }
        FileUploadBO fileUploadBO = new FileUploadBO();
        BeanUtils.copyProperties(fileUpload, fileUploadBO);
        setBaseAccount(fileUploadBO);
        String url = fileBusiness.upload(file, fileUploadBO);
        return ResponseEntity.buildSuccEntity(url);
    }

//    @GetMapping("download")
//    public org.springframework.http.ResponseEntity<byte[]> download(FileDownload fileDownload) {
//        FileDownloadBO fileDownloadBO = new FileDownloadBO();
//        BeanUtils.copyProperties(fileDownload, fileDownloadBO);
//        setBaseAccount(fileDownloadBO);
//        return fileBusiness.download(fileDownloadBO);
//    }

    //@GetMapping("getDownloadUrl")
    @PostMapping("getDownloadUrl")
    public ResponseEntity<String> download(@RequestBody FileDownload fileDownload) {
        FileDownloadBO fileDownloadBO = new FileDownloadBO();
        BeanUtils.copyProperties(fileDownload, fileDownloadBO);
        setBaseAccount(fileDownloadBO);
        String url = fileBusiness.download(fileDownloadBO);
        return ResponseEntity.buildSuccEntity(url);
    }

}
