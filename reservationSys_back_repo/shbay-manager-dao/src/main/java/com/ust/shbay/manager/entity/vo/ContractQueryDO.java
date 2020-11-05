package com.ust.shbay.manager.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContractQueryDO{
    //项目id
    private Integer projectId;

    //合同类型
    private Integer contractType;

    //项目类型（0：工程项目，1：规划调整，2：土地前期出让，3：项目前期报建）
    private Integer projectType;

    //合同状态（作废：-2，中止：-1，执行中：0，完成：1）
    private Integer status;

    //合同编号
    private String contractNum;

    //合同名称
    private String contractName;

    //项目编号
    private String projectNum;

    //项目名称
    private String projectName;

    //甲方
    private String firstParty;

    //乙方
    private String secondParty;

}
