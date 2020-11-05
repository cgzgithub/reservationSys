package com.ust.shbay.manager.api.controller.dto.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SendSms {

    //手机号
    @Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")
    @NotBlank(message = "手机号不能为空！")
    private String phone;

}
