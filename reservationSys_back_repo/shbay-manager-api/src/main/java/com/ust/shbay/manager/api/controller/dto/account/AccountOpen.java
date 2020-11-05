package com.ust.shbay.manager.api.controller.dto.account;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountOpen extends BaseToken {

    // 姓名
    @NotBlank(message = "姓名不能为空！")
    private String name;

    // 手机号
    @NotBlank(message = "手机号不能为空！")
    @Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")
    private String accountName;

    // 密码
    @NotBlank(message = "密码不能为空！")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$",message = "密码必须包含字母和数字，且长度在6-18位之间")
    private String password;

    // 角色ID
    private List<String> roleIdList;
}
