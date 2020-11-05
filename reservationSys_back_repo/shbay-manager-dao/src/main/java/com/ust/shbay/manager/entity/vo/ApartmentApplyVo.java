package com.ust.shbay.manager.entity.vo;


import com.ust.shbay.manager.entity.ApartmentApply;
import lombok.Data;

@Data
public class ApartmentApplyVo extends ApartmentApply {

    //单位名
    private String companyName;

    //户型字典名
    private String houseTypeName;

    //合同周期
    private String term;

    //到期类型：1月内，1-3个月，三个月以上
    private Integer dueDate;

    //公寓地址
    private String apartmentAddress;
}
