package com.ust.shbay.manager.api.controller.dto.contract;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContractNodeDel extends BaseToken {

    //id
    private Integer id;
}
