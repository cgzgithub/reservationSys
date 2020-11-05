package com.ust.shbay.manager.biz.Apartment.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApplyApartmentSearchBo extends BaseUser {
    //申请状态
    private  Integer status;
    //合同状态
    private Integer contractStatus;
    //到期类型
    private  Integer dueDate;
    //租房类型
    private  Integer houseType;
    //单位名称
    private  String  companyName;
    //个人姓名
    private  String  name;
    //页码
    private Integer pageNumber;

    //每页数量
    private Integer pageSize;

}
