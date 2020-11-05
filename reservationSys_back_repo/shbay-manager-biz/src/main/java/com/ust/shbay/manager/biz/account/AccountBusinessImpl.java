package com.ust.shbay.manager.biz.account;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.UUID;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.constant.MngConstant;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.manager.biz.account.bo.*;
import com.ust.shbay.manager.dao.*;
import com.ust.shbay.manager.entity.*;
import com.ust.shbay.manager.entity.vo.AccountVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class AccountBusinessImpl implements AccountBusiness {

    @Autowired
    private SyAccountMapper syAccountMapper;

    @Autowired
    private SyAccountRoleMapper syAccountRoleMapper;

    @Autowired
    private SyRoleMapper syRoleMapper;

    @Override
    public PageInfo<AccountVO> getAccount(AccountQueryBO accountQueryBO) {
        SyAccountExample example = new SyAccountExample();
        SyAccountExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(accountQueryBO.getAccount())) {
            criteria.andAccountLike("%" + accountQueryBO.getAccountName() + "%");
        }
        if (!StringUtils.isEmpty(accountQueryBO.getName())) {
            criteria.andNameLike("%" + accountQueryBO.getName() + "%");
        }

        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        example.setOrderByClause(" update_time desc ");
        PageHelper.startPage(accountQueryBO.getPageNum(), accountQueryBO.getPageSize());

        List<SyAccount> accountList = syAccountMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(accountList);
        List<AccountVO> accountVOList = new ArrayList<>();
        if (accountList.size() > 0) {
            for (SyAccount syAccount : accountList) {
                AccountVO accountVO = new AccountVO();
                BeanUtils.copyProperties(syAccount,accountVO);
                //处理角色
                List<SyRole> roleList = syRoleMapper.selectByAccountId(syAccount.getId());
                accountVO.setRoles(roleList);
                accountVOList.add(accountVO);
            }
        }
        pageInfo.setList(accountVOList);
        return pageInfo;
    }

    @Transactional
    @Override
    public void openAccount(AccountOpenBO accountOpenBO) {
        //判断账号是否被占用
        if(isUsed(accountOpenBO.getAccountName(),null)){
            throw new SException(MngConstant.Error.ACCOUNT_ISUSED_MSG);
        }

        //添加账号
        SyAccount syAccount = new SyAccount();
        String uuid = UUID.randomUUID().replace("-", "");
        syAccount.setId(uuid);
        syAccount.setAccount(accountOpenBO.getAccountName());
        syAccount.setName(accountOpenBO.getName());
        String md5Pwd = DigestUtils.md5DigestAsHex(accountOpenBO.getPassword().getBytes());
        syAccount.setPassword(md5Pwd);
        syAccount.setCreateUser(accountOpenBO.getAccount());
        syAccount.setUpdateUser(accountOpenBO.getAccount());
        syAccountMapper.insertSelective(syAccount);

        //添加账号角色
        saveAccountRole(accountOpenBO.getRoleIdList(),syAccount.getId(),accountOpenBO.getAccount());
    }

    @Transactional
    @Override
    public void editAccount(AccountEditBO accountEditBO) {
        //判断账号是否被占用
        if(isUsed(accountEditBO.getAccountName(),accountEditBO.getId())){
            throw new SException (MngConstant.Error.ACCOUNT_ISUSED_MSG);
        }

        //修改账号和角色
        String accountId = accountEditBO.getId();
        SyAccount syAccount = syAccountMapper.selectByPrimaryKey(accountId);
        if (syAccount != null) {
            SyAccount entity = new SyAccount();
            entity.setId(accountId);
            entity.setAccount(accountEditBO.getAccountName());
            entity.setName(accountEditBO.getName());
            if (!StringUtils.isEmpty(accountEditBO.getPassword())) {
                String md5Pwd = DigestUtils.md5DigestAsHex(accountEditBO.getPassword().getBytes());
                entity.setPassword(md5Pwd);
            }
            entity.setUpdateUser(accountEditBO.getAccount());
            syAccountMapper.updateByPrimaryKeySelective(entity);

            //更新账号角色
            saveAccountRole(accountEditBO.getRoleIdList(),syAccount.getId(),accountEditBO.getAccount());
        }else{
            throw new SException (MngConstant.Error.ACCOUNT_NON_EXISTENT_MSG);
        }
    }

    @Transactional
    @Override
    public void delAccount(AccountDelBO accountDelBO) {
        String accountId = accountDelBO.getId();
        SyAccount syAccount = syAccountMapper.selectByPrimaryKey(accountId);
        if (syAccount != null) {
            //删除账号角色
            List<String> roleIds = syRoleMapper.selectRoleIdsByAccountId(syAccount.getId());
            delAccountRole(roleIds, syAccount.getId(), accountDelBO.getAccount());
            //删除账号
            SyAccount entity = new SyAccount();
            entity.setId(accountId);
            entity.setDelFlag(Constant.Status.DEL);
            entity.setUpdateUser(accountDelBO.getAccount());
            syAccountMapper.updateByPrimaryKeySelective(entity);
        }else{
            throw new SException (MngConstant.Error.ACCOUNT_NON_EXISTENT_MSG);
        }
    }

    //开通、编辑账号时判断账号是否被占用
    private Boolean isUsed(String account,String accountId){
        Boolean flag = false;
        SyAccountExample example = new SyAccountExample();
        SyAccountExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<SyAccount> syAccountList = syAccountMapper.selectByExample(example);
        if(accountId == null){//开通
            if(syAccountList.size() > 0){
                //账号已经使用
                flag = true;
            }
        }else{//编辑
            if(syAccountList.size() > 0 && !accountId.equals(syAccountList.get(0).getId())){
                //账号已经使用
                flag = true;
            }
        }

        return flag;
    }

    //更新账号拥有的角色
    private void saveAccountRole(List<String> roleList, String accountId, String account) {

        //查询数据库已有角色
        List<String> oldRoleList = syRoleMapper.selectRoleIdsByAccountId(accountId);

        //如果全为空，不处理
        if ((roleList == null || roleList.size() == 0) && (oldRoleList == null || oldRoleList.size() == 0)) {
            return;
        }

        //如果数据库数据为空，全部插入
        if (oldRoleList == null || oldRoleList.size() == 0) {
            addAccountRole(roleList, accountId, account);
            return;
        }

        //如果传入为空，数据库数据全部删除
        if (roleList == null || roleList.size() == 0) {
            delAccountRole(oldRoleList, accountId, account);
            return;
        }

        List<String> copyRoleList = new ArrayList(roleList);
        //交集
        roleList.retainAll(oldRoleList);
        List<String> intersection = roleList;
        //数据库差集
        oldRoleList.removeAll(intersection);
        List<String> oldDifferenceSet = oldRoleList;
        //传入差集
        copyRoleList.removeAll(intersection);

        //交集不变，传入差集新增
        addAccountRole(copyRoleList, accountId, account);
        //数据库差集删除
        delAccountRole(oldDifferenceSet, accountId, account);
    }

    //添加账号角色
    @Override
    public void addAccountRole(List<String> roleIds, String accountId, String account) {
        if (roleIds != null) {
            for (String roleId : roleIds) {
                SyAccountRole syAccountRole = new SyAccountRole();
                syAccountRole.setRoleId(roleId);
                syAccountRole.setAccountId(accountId);
                syAccountRole.setCreateUser(account);
                syAccountRole.setUpdateUser(account);
                syAccountRoleMapper.insertSelective(syAccountRole);
            }
        }
    }

    //删除账号角色
    private void delAccountRole(List<String> roleIds, String accountId, String account) {
        if (roleIds != null && roleIds.size() > 0) {
            SyAccountRole entity = new SyAccountRole();
            entity.setDelFlag(Constant.Status.DEL);
            entity.setUpdateUser(account);

            SyAccountRoleExample example = new SyAccountRoleExample();
            SyAccountRoleExample.Criteria criteria = example.createCriteria();
            criteria.andAccountIdEqualTo(accountId);
            criteria.andRoleIdIn(roleIds);
            syAccountRoleMapper.updateByExampleSelective(entity,example);
        }
    }
}
