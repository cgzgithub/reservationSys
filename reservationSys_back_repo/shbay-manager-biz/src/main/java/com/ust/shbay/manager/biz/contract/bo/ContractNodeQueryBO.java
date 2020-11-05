package com.ust.shbay.manager.biz.contract.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContractNodeQueryBO extends BaseUser {

    //合同id
    private Integer contractId;
}
