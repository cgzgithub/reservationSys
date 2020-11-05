package com.ust.shbay.manager.api.controller.dto.role;

import com.ust.shbay.service.base.BaseToken;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleQuery extends BaseToken {

    // 角色名称
    private String roleName;

    // 当前页
    private Integer pageNum;

    // 页面数据条目
    private Integer pageSize;
}
