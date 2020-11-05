package com.ust.shbay.service.file.bo;

import com.ust.shbay.service.base.BaseUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FileUploadBO extends BaseUser {

    @ApiModelProperty(value = "project:工程项目分类,reservation:预定分类(会议室、展厅),other:其他")
    private String type;
}
