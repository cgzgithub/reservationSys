package com.ust.shbay.manager.biz.Meeting.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingEnclosureAddBO extends BaseUser {

    //会议室id
    private Integer meetingId;

    //会议室附件名
    private String name;

    //会议室附件数量
    private Integer num;
}