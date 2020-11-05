package com.ust.shbay.manager.api.controller.dto.company;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyDel extends BaseToken {
//    公司id
    private Integer id;
}
