package com.ust.shbay.manager.api.controller.dto.showroom;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GuideEdit extends BaseToken {

    @NotNull(message = "讲解员id不能为空！")
    private  Integer id;

    //讲解员姓名
    @NotBlank(message = "讲解员姓名不能为空！")
    private String name;

    //讲解员电话
    private String phone;
}