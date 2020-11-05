package com.ust.shbay.manager.api.controller.dto.company;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class VisitCompanyQuery extends BaseToken {

    private Integer isEnglish;
}
