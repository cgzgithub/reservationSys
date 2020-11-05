package com.ust.shbay.manager.biz.user.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ForgetPwdBO {

    //账号即手机号
    @NotBlank(message = "账号不能为空！")
    private String account;

    //验证码
    @NotBlank(message = "验证码不能为空！")
    private String code;

    @NotBlank(message = "密码不能为空！")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$",message = "密码必须包含字母和数字，且长度在6-18位之间")
    private String newPwd;

}
