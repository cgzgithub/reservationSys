package com.ust.shbay.manager.biz.Apartment.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentQueryBo extends BaseUser {
    //公寓状态（1已出租，0待出租）默认0
    private Integer status;
    private Integer houseType;
    private  String residentialAreaName;
    //页码
    private Integer pageNumber;
    //每页数量
    private Integer pageSize;

}
