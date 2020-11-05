package com.ust.shbay.manager.biz.project.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class StageNodeEditBO extends BaseUser {

    //记录id
    private Integer id;

    //项目id
    private Integer projectId;

    //阶段id
    private Integer stageId;

    //非自定义节点id
    private Integer stageNodeId;

    //自定义节点名称
    private String nodeName;

//    //分类（0阶段，1非自定义节点，2自定义及节点）
//    private Integer type;

    //记录人
    private String person;
}
