package com.ust.shbay.manager.entity.vo;

import com.ust.shbay.manager.entity.ContractNode;
import lombok.Data;

@Data
public class ContractNodeVO extends ContractNode {

    //付款金额（千分数）
    private String paymentMoneyStr;
}
