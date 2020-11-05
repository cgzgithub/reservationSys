package com.ust.shbay.manager.api.controller.dto.apartment;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 人才公寓审核查询
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentReviewHiQuery extends BaseToken {

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

    //页码
    private Integer pageNumber;

    //每页数量
    private Integer pageSize;

}
