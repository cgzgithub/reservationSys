package com.ust.shbay.manager.biz.project.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectAddBO extends BaseUser {

    //项目类型（0：工程项目，1：规划调整，2：土地前期出让，3：项目前期报建）
    private Integer projectType;

    //项目编号
    private String number;

    //项目名称
    private String name;

    //项目负责人
    private String person;

    //联系电话
    private String phone;

    //项目开始时间
    private Date beginTime;

    //项目结束时间
    private Date endTime;

    //项目总投资额
    @Column(precision = 14, scale = 2)
    private BigDecimal totalInvestment;
}
