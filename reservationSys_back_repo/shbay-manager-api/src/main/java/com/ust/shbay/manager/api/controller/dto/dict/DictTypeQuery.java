package com.ust.shbay.manager.api.controller.dto.dict;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DictTypeQuery extends BaseToken {

    //字典名称
    private String title;

}
