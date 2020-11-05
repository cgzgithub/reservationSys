package com.ust.shbay.manager.entity.vo;

import com.ust.shbay.manager.entity.Contract;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ContractVO extends Contract {

    //项目编号
    private String projectNum;

    //合同类型字典名称
    private String contractTypeName;

    //合同状态字典名称
    private String contractStatusName;

    //项目名称
    private String projectName;

    //项目类型（0：工程项目，1：规划调整，2：土地前期出让，3：项目前期报建）
    private Integer projectType;

    //累计已付金额
    @Column(precision = 14, scale = 2)
    private BigDecimal totalPaidMoney;

    //累计已付金额（千分数）
    private String totalPaidMoneyStr;

    //合同总金额（千分数）
    private String totalMoneyStr;

    //最终版结算价（千分数）
    private String finalMoneyStr;

    //未支付金额
    private String unpaidMoneyStr;

    //合同附件
    private List<TRelationVO> contractMaterials;
}
