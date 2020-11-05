package com.ust.shbay.manager.api.controller.dto.dict;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DictTypeDelete extends BaseToken {

    //id
    private Integer id;
}
