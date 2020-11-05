package com.ust.shbay.manager.api.controller.dto.account;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountEdit extends BaseToken {

    @NotNull(message = "账号id不能为空！")
    private String id;

    // 姓名
    @NotBlank(message = "姓名不能为空！")
    private String name;

    // 手机号
    @Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")
    @NotBlank(message = "手机号不能为空！")
    private String accountName;

    // 密码
//    @NotBlank(message = "密码不能为空！")
    private String password;

    // 角色ID
    private List<String> roleIdList;
}
