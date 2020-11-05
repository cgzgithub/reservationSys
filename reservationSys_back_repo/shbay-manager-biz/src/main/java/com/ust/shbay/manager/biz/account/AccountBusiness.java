package com.ust.shbay.manager.biz.account;

import com.github.pagehelper.PageInfo;
import com.ust.shbay.manager.biz.account.bo.*;
import com.ust.shbay.manager.entity.vo.AccountVO;

import java.util.List;

/**
 *
 */
public interface AccountBusiness {

    /**
     * 获取所有账户数据 (分页展示)
     *
     * @param accountQueryBO
     * @return
     */
    PageInfo<AccountVO> getAccount(AccountQueryBO accountQueryBO);

    /**
     * 账户开通
     *
     * @param accountOpenBO
     */
    void openAccount(AccountOpenBO accountOpenBO);

    /**
     * 账号编辑
     *
     * @param accountEditBO
     */
    void editAccount(AccountEditBO accountEditBO);

    /**
     * 账号删除
     *
     * @param accountDelBO
     */
    void delAccount(AccountDelBO accountDelBO);

    /**
     * 添加账号角色
     * @param roleIds
     * @param accountId
     * @param account
     */
    void addAccountRole(List<String> roleIds, String accountId, String account);
}
