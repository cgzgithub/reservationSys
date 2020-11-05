package com.ust.shbay.manager.biz.project.vo;

import com.ust.shbay.manager.entity.Project;
import lombok.Data;

@Data
public class ProjectVO extends Project {

    //项目周期
    private String cycle;

    //当前项目阶段名称
    private String stageName;

    //当前阶段节点名称
    private String stageNodeName;

    //项目总投资额
    private String totalInvestmentStr;

    //查询项目累计已签订合同金额
    private String totalContractMoneyStr;

    //查询项目累计已结算金额
    private String totalPaidMoneyStr;
}
