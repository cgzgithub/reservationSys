package com.ust.shbay.manager.api.controller.dto.showroom;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingAnalysis extends BaseToken {

    //开始日期
    private Date beginDate;

    //结束日期
    private Date endDate;
}
