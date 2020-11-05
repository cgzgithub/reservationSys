package com.ust.shbay.manager.api.controller.dto.meeting;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingBookingTimeQuery extends BaseToken {

    //查询日期
    @NotNull(message = "预约日期不能为空！")
    private Date visitDate;

    //会议室id
    private Integer meetingId;

    //查询天数
    private Integer num;
}
