package com.ust.shbay.manager.api.controller.dto.apartment;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentDel extends BaseToken {
    private Integer id;
}
