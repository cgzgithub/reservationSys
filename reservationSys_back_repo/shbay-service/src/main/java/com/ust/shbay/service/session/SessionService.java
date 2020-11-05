package com.ust.shbay.service.session;

import com.ust.shbay.common.enums.LoginChannel;
import com.ust.shbay.manager.entity.SyAccount;

/**
 *
 */
public interface SessionService {

    /**
     * 创建会话
     *
     * @param syAccount
     * @param channel
     * @return
     */
    String createSession(SyAccount syAccount, LoginChannel channel);

    /**
     * 删除会话
     *
     * @param syAccount
     * @param channel
     */
    void deleteSession(SyAccount syAccount, LoginChannel channel);

    /**
     * 根据session获取账户详情
     *
     * @param sessionCode
     * @return
     */
    SyAccount getAccountBySession(String sessionCode, LoginChannel channel);
}
