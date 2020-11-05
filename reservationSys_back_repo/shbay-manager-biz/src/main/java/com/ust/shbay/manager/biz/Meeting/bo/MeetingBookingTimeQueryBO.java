package com.ust.shbay.manager.biz.Meeting.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingBookingTimeQueryBO extends BaseUser {

    //会议室id
    private Integer meetingId;

    //查询日期
    private Date visitDate;

    //查询天数
    private Integer num;
}
