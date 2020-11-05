package com.ust.shbay.manager.biz.Apartment.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyQueryBo  extends BaseUser {
    //企业名称
    private String companyName;
    //页码
    private Integer pageNumber;
    //每页数量
    private Integer pageSize;
}
