package com.ust.shbay.manager.api.controller.dto.role;

import com.ust.shbay.service.base.BaseToken;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class LoginShowChange extends BaseToken {

    // 权限ID
    private Integer id;

    @ApiModelProperty(value = "0:不登录可见  1:登录可见")
    private Integer status;
}
