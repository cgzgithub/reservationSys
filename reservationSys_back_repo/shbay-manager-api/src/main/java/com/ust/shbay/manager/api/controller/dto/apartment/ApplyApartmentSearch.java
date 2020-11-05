package com.ust.shbay.manager.api.controller.dto.apartment;


import com.ust.shbay.service.base.BaseToken;
import lombok.Data;


@Data
public class ApplyApartmentSearch extends BaseToken {
    //申请状态
    private Integer status;
    //合同状态
    private Integer contractStatus;
    //到期类型
    private Integer dueDate;
    //租房类型
    private Integer houseType;
    //单位名称
    private String companyName;
    //个人姓名
    private String name;
    //页码
    private Integer pageNumber;
    //每页数量
    private Integer pageSize;
}
