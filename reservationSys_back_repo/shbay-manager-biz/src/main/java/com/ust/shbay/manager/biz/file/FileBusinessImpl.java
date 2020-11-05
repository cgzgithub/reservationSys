package com.ust.shbay.manager.biz.file;

import com.ust.shbay.common.UUID;
import com.ust.shbay.service.file.bo.FileDownloadBO;
import com.ust.shbay.service.file.FileService;
import com.ust.shbay.service.file.bo.FileUploadBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * .
 */
@Slf4j
@Service
public class FileBusinessImpl implements FileBusiness {

    @Autowired
    private FileService fileService;

    @Override
    public String upload(MultipartFile file, FileUploadBO fileUploadBO) {
        String uuid = UUID.randomUUID();

        return fileService.upload(file, fileUploadBO);

    }

    @Override
    public String download(FileDownloadBO fileDownloadBO) {
//        String fileUrl = fileDownloadBO.getFileUrl();
        return fileService.download(fileDownloadBO.getFilepath());
    }

    @Override
    public boolean delete(String key) {
        return fileService.delete(key);
    }

}
