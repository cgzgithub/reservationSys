package com.ust.shbay.manager.api.controller.dto.contract;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContractNodeEdit extends BaseToken {

    //id
    private Integer id;

    //合同id
    @NotNull(message = "合同id不能为空！")
    private Integer contractId;

    //付款节点名称
    @NotBlank(message = "节点名称不能为空！")
    private String paymentNode;

    //付款金额
    @NotNull(message = "付款金额不能为空！")
    @Column(precision = 14, scale = 2)
    private BigDecimal paymentMoney;

    //付款时间
    @NotNull(message = "付款时间不能为空！")
    private Date paymentTime;
}
