package com.ust.shbay.manager.api.controller.dto.file;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模板文件下载实体
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FileModelDownload extends BaseToken {
    //模板名称
    private String fileModelName;
}
