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
public class AccountRoleSaveBO extends BaseUser {

    // 账号ID
    private String accountId;

    // 前台角色ID
    private List<String> roleIds;
}
