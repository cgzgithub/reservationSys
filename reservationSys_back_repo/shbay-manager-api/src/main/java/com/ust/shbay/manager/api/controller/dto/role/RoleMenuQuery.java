package com.ust.shbay.manager.api.controller.dto.role;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleMenuQuery extends BaseToken {

    // 角色ID
    private String roleId;

}
