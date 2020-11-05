package com.ust.shbay.manager.api.controller.dto.project;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StageNodeAdd extends BaseToken {

    //项目id
    @NotNull(message = "项目id不能为空！")
    private Integer projectId;

    //阶段id
    @NotNull(message = "项目阶段不能为空！")
    private Integer stageId;

    //非自定义节点id
    private Integer stageNodeId;

    //自定义节点名称
    private String nodeName;

//    //分类（0阶段，1非自定义节点，2自定义及节点）
//    private Integer type;

    //记录人
    @NotBlank(message = "负责人不能为空！")
    private String person;

    //资料
    private List<TRelation> relationList;
}
