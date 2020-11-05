package com.ust.shbay.manager.biz.role.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * .
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleEditBO extends BaseUser {

    // 角色id
    private String id;

    // 角色名称
    private String roleName;

    // 角色描述
    private String roleDesc;

    // 菜单权限id
    private List<Integer> menuIds;
}
