package com.ust.shbay.manager.biz.role.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 *
 */
@Data
public class Role {

    // 角色id
    private String id;

    // 角色名称
    private String roleName;

    // 角色类型
    private Integer roleType;

    // 角色描述
    private String roleDesc;

    // 更新时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;
}
