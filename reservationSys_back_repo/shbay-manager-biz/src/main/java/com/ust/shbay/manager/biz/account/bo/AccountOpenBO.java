package com.ust.shbay.manager.biz.account.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountOpenBO extends BaseUser {

    // 员工
    private String name;

    // 账户名称
    private String accountName;

    // 密码
    private String password;

    // 角色ID
    private List<String> roleIdList;
}
