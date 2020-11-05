package com.ust.shbay.manager.api.controller.dto.file;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FileDownload extends BaseToken {

    // 文件ID
    private String filepath;
}
