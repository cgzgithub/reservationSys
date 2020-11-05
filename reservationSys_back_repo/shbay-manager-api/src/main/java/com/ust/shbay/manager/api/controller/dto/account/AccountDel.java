package com.ust.shbay.manager.api.controller.dto.account;

import com.ust.shbay.service.base.BaseToken;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountDel extends BaseToken {

    // 账号ID
    private String id;
}
