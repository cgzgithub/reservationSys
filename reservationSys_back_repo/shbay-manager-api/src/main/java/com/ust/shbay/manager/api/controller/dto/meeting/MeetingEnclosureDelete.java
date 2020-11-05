package com.ust.shbay.manager.api.controller.dto.meeting;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 删除会议室附件
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingEnclosureDelete extends BaseToken {

    //会议室附件id
    private Integer id;
}
