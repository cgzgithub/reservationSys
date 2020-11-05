package com.ust.shbay.service.account;

import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.exception.SException ;
import com.ust.shbay.manager.dao.SyAccountMapper;
import com.ust.shbay.manager.dao.SyAccountRoleMapper;
import com.ust.shbay.manager.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 *
 */

@Service("AccountServiceImpl")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private SyAccountMapper syAccountMapper;

    @Autowired
    private SyAccountRoleMapper syAccountRoleMapper;

    @Override
    public SyAccount getAccount(String account) {

        //if (Strings.isNullOrEmpty(account)){
        if (account == null || account.isEmpty()){
            return null;
        }
        SyAccountExample example = new SyAccountExample();
        SyAccountExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<SyAccount> syAccountList = syAccountMapper.selectByExample(example);
        if (syAccountList.size() > 0) {
            SyAccount syAccount = syAccountList.get(0);
            return syAccount;
        }
        return null;
    }

    @Transactional
    @Override
    public SyAccount auth(String username, String password) {
        //校验密码不能为空
        if(password == null || "".equals(password)){
            throw new SException ("登录失败，密码不能为空");
        }

        SyAccountExample example = new SyAccountExample();
        SyAccountExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(username);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);

        List<SyAccount> syAccountList = syAccountMapper.selectByExample(example);
        if (syAccountList.size() > 0) {
            SyAccount syAccount = syAccountList.get(0);
            String md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes());
            if (syAccount.getPassword().equals(md5Pwd)) {
                return syAccount;
            }
        }
        return null;
    }

}
