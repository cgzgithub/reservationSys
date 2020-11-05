package com.ust.shbay.manager.api.controller.dto.project;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArchivingAdd extends BaseToken {

    @NotNull(message = "项目id不能为空！")
    private Integer projectId;

    private Integer stageId;

    private Integer stageNodeId;

    @NotNull(message = "上传资料不能为空！")
    private List<TRelation> url;
}
