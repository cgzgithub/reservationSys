package com.ust.shbay.manager.api.controller.dto.meeting;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class TimeDecide extends BaseToken {

    //预约日期
    private Date visitDate;

    //开始时间
    private String beginTime;

}
