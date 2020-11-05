package com.ust.shbay.manager.biz.role.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleMenuSaveBO extends BaseUser {

    // 角色ID
    private String roleId;

    // 权限ID
    private List<Integer> menuIdList;
}
