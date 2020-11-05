package com.ust.shbay.manager.biz.project.bo;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArchivingEditBO extends BaseUser {
    private Integer id;
    private Integer projectId;
    private Integer stageId;
    private Integer stageNodeId;
    private List<TRelation> url;
}
