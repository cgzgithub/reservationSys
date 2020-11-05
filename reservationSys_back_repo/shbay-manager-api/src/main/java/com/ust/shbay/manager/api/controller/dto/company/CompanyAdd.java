package com.ust.shbay.manager.api.controller.dto.company;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyAdd extends BaseToken {

    //信用识别代码
    @NotBlank(message = "信用识别代码不能为空！")
    private String creditRecognitionId;

    //单位名称
    @NotBlank(message = "单位名称不能为空！")
    private String companyName;

    //总套数
    @NotNull(message = "总套数不能为空！")
    private Integer totalNumber;
}
