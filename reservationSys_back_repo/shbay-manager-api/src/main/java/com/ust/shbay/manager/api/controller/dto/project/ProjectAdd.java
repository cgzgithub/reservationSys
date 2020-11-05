package com.ust.shbay.manager.api.controller.dto.project;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectAdd extends BaseToken {

    //项目类型（0：工程项目，1：规划调整，2：土地前期出让，3：项目前期报建）
    @NotNull(message = "项目类型不能为空！")
    private Integer projectType;

    //项目编号
    @NotBlank(message = "项目编号不能为空！")
    private String number;

    //项目名称
    @NotBlank(message = "项目名称不能为空！")
    private String name;

    //项目负责人
    @NotBlank(message = "项目负责人不能为空！")
    private String person;

    //联系电话
    @NotBlank(message = "联系电话不能为空！")
    private String phone;

    //项目开始时间
    @NotNull(message = "项目开始时间不能为空！")
    private Date beginTime;

    //项目结束时间
    @NotNull(message = "项目结束时间不能为空！")
    private Date endTime;

    //项目总投资额
    @Column(precision = 14, scale = 2)
    private BigDecimal totalInvestment;

}
