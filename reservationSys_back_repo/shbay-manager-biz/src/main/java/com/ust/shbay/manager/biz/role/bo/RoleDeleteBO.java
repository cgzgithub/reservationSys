package com.ust.shbay.manager.biz.role.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * .
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleDeleteBO extends BaseUser {

    // 角色id
    private String id;

}
