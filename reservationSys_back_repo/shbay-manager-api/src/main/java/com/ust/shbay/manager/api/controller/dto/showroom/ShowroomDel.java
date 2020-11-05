package com.ust.shbay.manager.api.controller.dto.showroom;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomDel extends BaseToken {
    private  Integer id;
}
