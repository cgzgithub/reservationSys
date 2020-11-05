package com.ust.shbay.manager.biz.account.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * .
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountDelBO extends BaseUser {

    // 账户id
    private String id;
}
