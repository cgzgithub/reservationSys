package com.ust.shbay.manager.api.controller.dto.dict;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DictInfoDelete extends BaseToken {

    //ids
    private List<Integer> ids;
}
