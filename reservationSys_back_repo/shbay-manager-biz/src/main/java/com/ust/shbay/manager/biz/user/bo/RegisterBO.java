package com.ust.shbay.manager.biz.user.bo;

import lombok.Data;

/**
 *
 */

@Data
public class RegisterBO{

    // 员工
    private String name;

    // 账户名称
    private String accountName;

    // 密码
    private String password;

    // 验证码
    private String code;
}
