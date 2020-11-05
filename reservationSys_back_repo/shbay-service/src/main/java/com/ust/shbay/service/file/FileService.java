package com.ust.shbay.service.file;

import com.ust.shbay.service.file.bo.FileUploadBO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param file
     * @param fileUploadBO
     * @return
     */
    String upload(MultipartFile file, FileUploadBO fileUploadBO);

    /**
     * 文件下载
     *
     * @param filepath
     * @return 下载链接
     */
    String download(String filepath);

    /**
     * 文件删除
     *
     * @param key
     * @return Boolean
     */
    boolean delete (String key);

}
