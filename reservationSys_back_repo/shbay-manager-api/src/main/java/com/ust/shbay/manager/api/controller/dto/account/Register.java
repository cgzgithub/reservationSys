package com.ust.shbay.manager.api.controller.dto.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class Register {

    // 员工
    private String name;

    // 账户名称
    @Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")
    @NotBlank(message = "手机号不能为空！")
    private String accountName;

    // 密码
    @NotBlank(message = "密码不能为空！")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$",message = "密码必须包含字母和数字，且长度在6-18位之间")
    private String password;

    // 验证码
    @NotBlank(message = "验证码不能为空！")
    private String code;
}
