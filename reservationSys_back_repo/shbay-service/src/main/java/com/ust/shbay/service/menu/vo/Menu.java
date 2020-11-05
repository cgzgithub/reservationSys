package com.ust.shbay.service.menu.vo;

import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class Menu {

    private Integer id;

    // 菜单名称
    private String text;

    // 路径
    private String path;
//
//    // 菜单对应组件模块
//    private String component;
//
//    // 跳转
//    private String redirectPath;
//
//    // 是否展示
//    private boolean alwaysShow;
//
//    // 元
//    private Meta meta;

    // 子菜单
//    private List<SubMenu> children;
    private List<Menu> subMenu;

    //完整路径
    private String fullPath;
}
