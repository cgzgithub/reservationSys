package com.ust.shbay.manager.api.controller.dto.apartment;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentQuery extends BaseToken {
    //公寓状态（1已出租，0待出租）默认0
    private Integer status;
    private Integer houseType;
    private  String residentialAreaName;
    //页码
    private Integer pageNumber;
    //每页数量
    private Integer pageSize;



}
