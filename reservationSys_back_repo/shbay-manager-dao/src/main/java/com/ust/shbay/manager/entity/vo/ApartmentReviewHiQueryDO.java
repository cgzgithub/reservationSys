package com.ust.shbay.manager.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentReviewHiQueryDO {
    //审核阶段
    private Integer stage;

    //租房类型
    private Integer houseType;

    //个人姓名
    private String name;

    //个人姓名
    private String companyName;

    //当前申请状态
    private Integer applyStatus;

    //本阶段审核结果
    private Integer reviewStatus;
}
