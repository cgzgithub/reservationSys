package com.ust.shbay.manager.api.controller.dto.company;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyQuery extends BaseToken {
    //企业名称
    private  String companyName;
    //页码
    private Integer pageNumber;
    //每页数量
    private Integer pageSize;
}
