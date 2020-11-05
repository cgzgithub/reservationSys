package com.ust.shbay.manager.api.controller.dto.meeting;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingRoomOpen extends BaseToken {
    private  Integer id;
}
