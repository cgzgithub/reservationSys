package com.ust.shbay.service.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BaseToken {

    // 登录token
    @ApiModelProperty(name="token",value="登录token", required=true)
    private String token;
}
