package com.ust.shbay.service.file.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 可以删除
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FileDownloadBO extends BaseUser {

    // 文件ID
    private String filepath;
}
