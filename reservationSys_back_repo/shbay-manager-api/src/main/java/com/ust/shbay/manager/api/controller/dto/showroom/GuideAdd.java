package com.ust.shbay.manager.api.controller.dto.showroom;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GuideAdd extends BaseToken {

    //讲解员姓名
    @NotBlank(message = "讲解员姓名不能为空！")
    private String name;

    //讲解员电话
    private String phone;
}
