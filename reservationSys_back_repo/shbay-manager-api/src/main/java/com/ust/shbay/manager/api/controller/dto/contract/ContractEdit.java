package com.ust.shbay.manager.api.controller.dto.contract;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContractEdit extends BaseToken {

    //合同id
    @NotNull(message = "合同id不能为空！")
    private Integer id;

//    //项目id
//    @NotNull(message = "项目id不能为空！")
//    private Integer projectId;

    //合同编号
    @NotBlank(message = "合同编号不能为空！")
    private String contractNum;

    //合同名称
    @NotBlank(message = "合同名称不能为空！")
    private String contractName;

    //合同类型
    @NotNull(message = "合同类型不能为空！")
    private Integer contractType;

    //甲方
    @NotBlank(message = "甲方不能为空！")
    private String firstParty;

    //乙方
    @NotBlank(message = "乙方不能为空！")
    private String secondParty;

    //合同签订时间
    private Date signTime;

    //签订人
    private String signPerson;

    //合同开始时间
    @NotNull(message = "合同开始时间不能为空！")
    private Date beginTime;

    //合同结束时间
    @NotNull(message = "合同开始时间不能为空！")
    private Date endTime;

    //合同状态
    @NotNull(message = "合同状态不能为空！")
    private Integer status;

    //合同总金额
    @NotNull(message = "合同总金额不能为空！")
    @Column(precision = 14, scale = 2)
    private BigDecimal totalMoney;

    //合同最终结算价
    @Column(precision = 14, scale = 2)
    private BigDecimal finalMoney;

    //合同附件
    private List<TRelation> contractMaterials;
}
