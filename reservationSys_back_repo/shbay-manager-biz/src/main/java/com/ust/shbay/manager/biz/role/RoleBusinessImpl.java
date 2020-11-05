package com.ust.shbay.manager.biz.role;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.UUID;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.manager.biz.role.bo.*;
import com.ust.shbay.manager.dao.*;
import com.ust.shbay.manager.entity.*;

import com.ust.shbay.manager.entity.vo.SyRoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class RoleBusinessImpl implements RoleBusiness {

    @Autowired
    private SyRoleMapper syRoleMapper;

    @Autowired
    private SyRoleMenuMapper syRoleMenuMapper;

    @Override
    public PageInfo getRole(RoleQueryBO roleQueryBO) {
        SyRoleExample syRoleExample = new SyRoleExample();
        SyRoleExample.Criteria criteria = syRoleExample.createCriteria();
        if (!StringUtils.isEmpty(roleQueryBO.getRoleName())) {
            criteria.andRoleNameLike("%" + roleQueryBO.getRoleName() + "%");
        }
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        syRoleExample.setOrderByClause("update_time desc");
        if (roleQueryBO.getPageNum() != null && roleQueryBO.getPageSize() != null) {
            PageHelper.startPage(roleQueryBO.getPageNum(), roleQueryBO.getPageSize());
        }
        List<SyRole> syRoleList = syRoleMapper.selectByExample(syRoleExample);
        List<SyRoleVO> syRoleVOList = new ArrayList<>();
        for (SyRole syRole : syRoleList) {
            SyRoleVO syRoleVO = new SyRoleVO();
            BeanUtils.copyProperties(syRole, syRoleVO);
            //查询有权限的菜单id
            syRoleVO.setMenuIds(getRoleMenu(syRoleVO.getId()));
            syRoleVOList.add(syRoleVO);
        }
        PageInfo pageInfo = new PageInfo<>(syRoleVOList);
        return pageInfo;
    }

    @Transactional
    @Override
    public void addRole(RoleAddBO roleAddBO) {
        SyRole syRole = new SyRole();
        String uuid = UUID.randomUUID().replace("-", "");

        BeanUtils.copyProperties(roleAddBO, syRole);
        syRole.setId(uuid);
        syRole.setCreateUser(roleAddBO.getAccount());
        syRole.setUpdateUser(roleAddBO.getAccount());
        syRoleMapper.insertSelective(syRole);

        //添加角色菜单权限
        addSyRoleMenu(roleAddBO.getMenuIds(), uuid, roleAddBO.getAccount());
    }

    @Transactional
    @Override
    public void editRole(RoleEditBO roleEditBO) {
        SyRole syRole = syRoleMapper.selectByPrimaryKey(roleEditBO.getId());
        if (syRole != null) {
            SyRole entity = new SyRole();
            entity.setId(roleEditBO.getId());
            entity.setRoleName(roleEditBO.getRoleName());
            entity.setRoleDesc(roleEditBO.getRoleDesc());
            entity.setUpdateUser(roleEditBO.getAccount());
            syRoleMapper.updateByPrimaryKeySelective(entity);

            //更新菜单
            saveRoleMenu(roleEditBO.getId(), roleEditBO.getMenuIds(), roleEditBO.getAccount());
        } else {
            throw new SException("角色不存在！");
        }
    }

    @Transactional
    @Override
    public void deleteRole(RoleDeleteBO roleDeleteBO) {
        SyRole syRole = syRoleMapper.selectByPrimaryKey(roleDeleteBO.getId());
        if (syRole != null) {
            //校验是否为系统角色，系统角色无法删除
            if(syRole.getRoleType() != null && syRole.getRoleType() != Constant.ROLE_TYPE.COMMOM){
                throw new SException("该角色为系统角色，无法删除！");
            }

            //校验角色是否使用
            checkIsUsed(syRole.getId());

            SyRole entity = new SyRole();
            entity.setId(roleDeleteBO.getId());
            entity.setDelFlag(0);
            entity.setUpdateUser(roleDeleteBO.getAccount());
            syRoleMapper.updateByPrimaryKeySelective(entity);

            //删除角色菜单
            List<Integer> menuIds = getRoleMenu(roleDeleteBO.getId());
            if (menuIds != null) {
                delSyRoleMenu(menuIds, roleDeleteBO.getId(), roleDeleteBO.getAccount());
            }
        } else {
            throw new SException("角色不存在！");
        }
    }

    private void checkIsUsed(String roleId) {
        //查询账号角色表
        List<String> accountIds = syRoleMapper.selectAccountIdsByRoleId(roleId);
        if (accountIds != null && accountIds.size() > 0) {
            throw new SException("角色已经使用无法删除！");
        }
    }

    @Override
    public List<Integer> getRoleMenu(String roleId) {
        List<Integer> menulist = new ArrayList<>();

        SyRoleMenuExample example = new SyRoleMenuExample();
        SyRoleMenuExample.Criteria criteria = example.createCriteria();
        criteria.andRoleIdEqualTo(roleId);
        criteria.andDelFlagEqualTo(1);
        List<SyRoleMenu> syRoleMenuList = syRoleMenuMapper.selectByExample(example);
        for (SyRoleMenu syRoleMenu : syRoleMenuList) {
            menulist.add(syRoleMenu.getMenuId());
        }
        return menulist;
    }

    @Override
    public void saveRoleMenu(String roleId, List<Integer> menuList, String account) {

        //查询数据库已有菜单权限
        List<Integer> oldMenuList = getRoleMenu(roleId);

        //如果全为空，不处理
        if ((oldMenuList == null || oldMenuList.size() == 0) && (menuList == null || menuList.size() == 0)) {
            return;
        }

        //如果数据库数据为空，全部插入
        if (oldMenuList == null || oldMenuList.size() == 0) {
            addSyRoleMenu(menuList, roleId, account);
            return;
        }

        //如果传入为空，数据库数据全部删除
        if (menuList == null || menuList.size() == 0) {
            delSyRoleMenu(oldMenuList, roleId, account);
            return;
        }

        List<Integer> copyNewMenuList = new ArrayList(menuList);
        //交集
        menuList.retainAll(oldMenuList);
        List<Integer> intersection = menuList;
        //数据库差集
        oldMenuList.removeAll(intersection);
        List<Integer> oldDifferenceSet = oldMenuList;
        //传入差集
        copyNewMenuList.removeAll(intersection);

        //交集不变，传入差集新增
        addSyRoleMenu(copyNewMenuList, roleId, account);
        //数据库差集删除
        delSyRoleMenu(oldDifferenceSet, roleId, account);
    }

    private void addSyRoleMenu(List<Integer> menuIdList, String roleId, String account) {
        if (menuIdList != null) {
            for (Integer menuId : menuIdList) {
                SyRoleMenu syRoleMenu = new SyRoleMenu();
                syRoleMenu.setRoleId(roleId);
                syRoleMenu.setMenuId(menuId);
                syRoleMenu.setCreateUser(account);
                syRoleMenu.setUpdateUser(account);
                syRoleMenuMapper.insertSelective(syRoleMenu);
            }
        }
    }

    private void delSyRoleMenu(List<Integer> menuIdList, String roleId, String account) {
        if (menuIdList != null && menuIdList.size() > 0) {

            SyRoleMenu entity = new SyRoleMenu();
            entity.setDelFlag(Constant.Status.DEL);
            entity.setUpdateUser(account);

            SyRoleMenuExample example = new SyRoleMenuExample();
            SyRoleMenuExample.Criteria criteria = example.createCriteria();
            criteria.andRoleIdEqualTo(roleId);
            criteria.andMenuIdIn(menuIdList);

            syRoleMenuMapper.updateByExampleSelective(entity, example);

        }
    }
}
