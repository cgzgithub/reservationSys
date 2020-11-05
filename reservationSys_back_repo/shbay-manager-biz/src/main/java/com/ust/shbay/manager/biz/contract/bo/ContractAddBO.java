package com.ust.shbay.manager.biz.contract.bo;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContractAddBO extends BaseUser {
    //项目id
    private Integer projectId;

    //合同编号
    private String contractNum;

    //合同名称
    private String contractName;

    //合同类型
    private Integer contractType;

    //甲方
    private String firstParty;

    //乙方
    private String secondParty;

    //合同签订时间
    private Date signTime;

    //签订人
    private String signPerson;

    //合同开始时间
    private Date beginTime;

    //合同结束时间
    private Date endTime;

    //合同状态
    private Integer status;

    //合同总金额
    @Column(precision = 14, scale = 2)
    private BigDecimal totalMoney;

    //合同附件
    private List<TRelation> contractMaterials;
}
