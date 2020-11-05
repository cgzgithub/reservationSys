package com.ust.shbay.manager.api.controller.dto.project;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArchivingSearch  extends BaseToken {
    private Integer projectType;
    private Integer projectId;
    private Integer stageId;
    private Integer stageNodeId;
    private String createUser;
    private String updateUser;
    private Integer pageNumber;
    private Integer pageSize;
}
