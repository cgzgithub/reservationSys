package com.ust.shbay.manager.api.controller.dto.project;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class StageNodeDictQuery extends BaseToken {
    //项目类型
    private Integer projectType;
}
