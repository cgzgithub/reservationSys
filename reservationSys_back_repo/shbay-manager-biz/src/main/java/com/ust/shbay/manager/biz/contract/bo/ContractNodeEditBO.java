package com.ust.shbay.manager.biz.contract.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContractNodeEditBO extends BaseUser {
    //id
    private Integer id;

    //合同id
    private Integer contractId;

    //付款节点名称
    private String paymentNode;

    //付款金额
    @Column(precision = 14, scale = 2)
    private BigDecimal paymentMoney;

    //付款时间
    private Date paymentTime;
}
