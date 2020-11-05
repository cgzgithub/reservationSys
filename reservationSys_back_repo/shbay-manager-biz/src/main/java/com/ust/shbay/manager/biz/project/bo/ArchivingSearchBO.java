package com.ust.shbay.manager.biz.project.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArchivingSearchBO extends BaseUser {
    private Integer projectType;
    private Integer projectId;
    private Integer stageId;
    private Integer stageNodeId;
    private String createUser;
    private String updateUser;
    private Integer pageNumber;
    private Integer pageSize;
}
