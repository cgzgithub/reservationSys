package com.ust.shbay.manager.biz.role.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleQueryBO extends BaseUser {

    // 角色名称
    private String roleName;
    
    // 当前页
    private Integer pageNum;

    // 页面数据条目
    private Integer pageSize;
}
