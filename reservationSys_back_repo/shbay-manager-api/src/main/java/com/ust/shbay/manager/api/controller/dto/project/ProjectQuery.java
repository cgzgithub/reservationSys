package com.ust.shbay.manager.api.controller.dto.project;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectQuery extends BaseToken {

    //项目类型
    private Integer projectType;

    //项目阶段字典id
    private List<Integer> stageIds;

    //项目阶段节点字典id
    private List<Integer> stageNodeIds;

    //项目编号
    private String number;

    //项目名称
    private String name;

    //项目负责人
    private String person;

    //页码
    private Integer pageNumber;

    //每页数量
    private Integer pageSize;

    //删除标记
    private List<Integer> delFlag;

}
