package com.ust.shbay.service.account;

import com.ust.shbay.manager.entity.SyAccount;

/**
 *
 */

//@Service
public interface AccountService {

    /**
     * 根据账户获取员工信息
     *
     * @param account
     * @return
     */
    SyAccount getAccount(String account);

    /**
     * 校验用户
     *
     * @param username
     * @param password
     * @return
     */
    SyAccount auth(String username, String password);
}
