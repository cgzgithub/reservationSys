package com.ust.shbay.manager.api.controller.dto.account;

import com.ust.shbay.service.base.BaseToken;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ChangePwd extends BaseToken {

    @NotBlank(message = "密码不能为空！")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$",message = "密码必须包含字母和数字，且长度在6-18位之间")
    private String newPwd;

}
