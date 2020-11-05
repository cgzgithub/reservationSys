package com.ust.shbay.manager.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class TRelation {
    //id
    private Integer id;

    //类型 （putRequirementPic、meetingVisit、showroomVisit)
    private String type;

    //原信息id
    private Integer originalId;

    //文件名
    private String fileName;

    //七牛云path
    private String fileUrl;

    //删除标记（1：正常，0：删除）
    private Integer delFlag;

    //创建时间
    private Date createTime;

    //创建人
    private String createUser;

    //修改时间
    private Date updateTime;

    //修改人
    private String updateUser;
}