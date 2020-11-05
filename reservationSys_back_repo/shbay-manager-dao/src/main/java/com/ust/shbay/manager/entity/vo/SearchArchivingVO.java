package com.ust.shbay.manager.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchArchivingVO {

    private Integer id;

    //项目id
    private Integer projectId;

    //项目名称
    private String projectName;

    //阶段id
    private Integer stageId;

    //阶段名
    private String stageName;

    //阶段节点id
    private Integer stageNodeId;

    //阶段节点名
    private String stageNodeName;

    //上传人
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //上传时间
    private String createUser;

    //最后修改时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    //最后修改人
    private String updateUser;

    //上传附件
    private List<TRelationVO> url;
}
