package com.ust.shbay.manager.api.controller.dto.project;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class StageQuery extends BaseToken {
    //项目id
    private Integer projectId;

    //阶段id
    private Integer stageId;
}
