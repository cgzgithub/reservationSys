package com.ust.shbay.manager.biz.Meeting.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingBookingCancelBO extends BaseUser {
    //id
    private Integer id;

    //取消通道
    private Integer cancelChannel;

    //取消原因字典id
    private Integer cancelReasonId;
}
