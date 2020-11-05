package com.ust.shbay.manager.entity.vo;

import lombok.Data;

@Data
public class HomePageMenuVO {

    //一级菜单名
    private String name;

    //跳转路径，二级菜单完整路径
    private String subMenuFullPath;

    //icon
    private String icon;
}
