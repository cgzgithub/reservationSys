package com.ust.shbay.manager.entity.vo;

import com.ust.shbay.manager.entity.SyRole;
import lombok.Data;

import java.util.List;

@Data
public class SyRoleVO extends SyRole {
    //有权限的菜单id
    private List<Integer> menuIds;
}
