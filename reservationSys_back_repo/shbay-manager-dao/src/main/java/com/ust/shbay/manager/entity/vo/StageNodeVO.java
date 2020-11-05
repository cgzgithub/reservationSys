package com.ust.shbay.manager.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class StageNodeVO {

    //项目阶段历史记录id
    private Integer id;

    //项目阶段(节点)名
    private String dictName;

    //项目阶段（节点）记录人
    private String person;

    //项目阶段（节点）记录时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    //阶段类型（0不可自定义节点，1可自定义节点如施工阶段）
    private Integer stageType;

    //节点是否结束
    private Boolean isFinished;

    //是否为最后一个节点
    private Boolean isLast;

    //资料
    private List<TRelationVO> relationVOList;

    //项目节点集合
    private List<StageNodeVO> childList;

}
