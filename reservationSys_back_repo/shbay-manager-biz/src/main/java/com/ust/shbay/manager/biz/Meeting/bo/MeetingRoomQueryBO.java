package com.ust.shbay.manager.biz.Meeting.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingRoomQueryBO extends BaseUser {

    //true:会议室，false：路演厅
    private Boolean type;

    //页码
    private Integer pageNumber;

    //每页数量
    private Integer pageSize;
}