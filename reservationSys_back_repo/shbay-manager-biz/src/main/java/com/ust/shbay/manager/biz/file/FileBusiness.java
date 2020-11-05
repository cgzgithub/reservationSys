package com.ust.shbay.manager.biz.file;

import com.ust.shbay.service.file.bo.FileDownloadBO;
import com.ust.shbay.service.file.bo.FileUploadBO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
public interface FileBusiness {

    /**
     * 上传文件
     *
     * @param file
     * @param fileUploadBO
     * @return 文件url
     */
    String upload(MultipartFile file, FileUploadBO fileUploadBO);

    /**
     * 下载文件
     *
     * @param fileDownloadBO
     */
    String download(FileDownloadBO fileDownloadBO);

    /**
     * 文件删除
     *
     * @param key
     * @return Boolean
     */
    boolean delete (String key);


}
