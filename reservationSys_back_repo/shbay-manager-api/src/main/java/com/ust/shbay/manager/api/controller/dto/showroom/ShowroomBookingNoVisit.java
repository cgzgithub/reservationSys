package com.ust.shbay.manager.api.controller.dto.showroom;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingNoVisit extends BaseToken {
    //id
    private Integer id;
}
