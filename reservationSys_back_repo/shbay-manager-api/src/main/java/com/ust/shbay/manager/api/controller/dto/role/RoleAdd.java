package com.ust.shbay.manager.api.controller.dto.role;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleAdd extends BaseToken {

    // 角色名称
    @NotBlank(message = "角色名称不能为空！")
    private String roleName;

    // 角色描述
    private String roleDesc;

    // 菜单权限id
    private List<Integer> menuIds;
}
