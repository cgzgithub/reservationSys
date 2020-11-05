package com.ust.shbay.manager.api.controller.dto.meeting;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 编辑会议室附件
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingEnclosureQuery extends BaseToken {

    //会议室id
    private Integer meetingId;

}
