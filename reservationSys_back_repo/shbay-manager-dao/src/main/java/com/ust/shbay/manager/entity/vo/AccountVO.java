package com.ust.shbay.manager.entity.vo;

import com.ust.shbay.manager.entity.SyRole;
import lombok.Data;

import java.util.List;

@Data
public class AccountVO {

    //角色列表
    private List<SyRole> roles;

    //账号id
    private String id;

    //姓名
    private String name;

    //账号
    private String account;

}
