package com.ust.shbay.manager.api.controller.dto.file;

import com.ust.shbay.service.base.BaseToken;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FileUpload extends BaseToken {

    @ApiModelProperty(value = "project:工程项目分类,reservation:预定分类(会议室、展厅),other:其他")
    private String type;
}
