package com.ust.shbay.manager.entity.vo;

import com.ust.shbay.manager.entity.NodeDict;
import lombok.Data;

import java.util.List;

@Data
public class StageNodeDictVO {

    //阶段id
    private Integer id;

    //项目阶段名
    private String nodeName;

    //项目节点集合
    private List<NodeDict> childList;

}
