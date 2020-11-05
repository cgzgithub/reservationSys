
package com.ust.shbay.manager.entity.vo.userDto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class PcUserLogin {

    @Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")
    @NotBlank(message = "手机号不能为空！")
    private String phone;

    //加密后的验证码
    @NotBlank(message = "验证码不能为空！")
    private String verificationCode;

}
