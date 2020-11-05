package com.ust.shbay.manager.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Guide {
    //讲解人id
    private  Integer id;
    //讲解人手机号
    private String phone;
    //讲解人姓名
    private String name;
    //是否删除标志 1为未删除
    private Integer delFlag;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
}
