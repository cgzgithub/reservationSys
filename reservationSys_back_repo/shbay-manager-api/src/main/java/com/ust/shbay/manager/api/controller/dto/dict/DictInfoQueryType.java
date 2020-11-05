package com.ust.shbay.manager.api.controller.dto.dict;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DictInfoQueryType extends BaseToken {
    private String type;
}