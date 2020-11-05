package com.ust.shbay.manager.api.controller.dto.meeting;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingRoomQuery extends BaseToken {
    //true:会议室，false：路演厅
    private Boolean type;

    //页码
    private Integer pageNumber;

    //每页数量
    private Integer pageSize;
}