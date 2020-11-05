package com.ust.shbay.manager.biz.role;

import com.github.pagehelper.PageInfo;
import com.ust.shbay.manager.biz.role.bo.*;
import com.ust.shbay.manager.entity.SyRole;
import com.ust.shbay.service.base.BaseUser;

import java.util.HashSet;
import java.util.List;

/**
 * .
 */
public interface RoleBusiness {

    /**
     * 获取角色(分页展示)
     *
     * @param roleQueryBO
     * @return
     */
    PageInfo getRole(RoleQueryBO roleQueryBO);

    /**
     * 添加角色
     *
     * @param roleAddBO
     */
    void addRole(RoleAddBO roleAddBO);

    /**
     * 编辑角色
     *
     * @param roleEditBO
     */
    void editRole(RoleEditBO roleEditBO);

    /**
     * 删除角色
     *
     * @param roleDeleteBO
     */
    void deleteRole(RoleDeleteBO roleDeleteBO);

    /**
     * 保存角色菜单
     * @param roleId
     * @param menuIdList
     * @param account
     */
    void saveRoleMenu(String roleId,List<Integer> menuIdList,String account);

    /**
     * 获取角色菜单列表
     *
     * @param roleId
     * @return
     */
    List<Integer> getRoleMenu(String roleId);

}
