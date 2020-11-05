package com.ust.shbay.manager.api.controller.dto.role;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleMenuSave extends BaseToken {

    // 角色ID
    private String roleId;

    // 权限ID
    private List<Integer> menuIdList;
}
