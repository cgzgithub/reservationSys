package com.ust.shbay.manager.api.controller.dto.meeting;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加会议室附件
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingEnclosureAdd extends BaseToken {

    //会议室id
    @NotNull(message = "会议室id不能为空！")
    private Integer meetingId;

    //会议室附件名
    @NotBlank(message = "附件名不能为空！")
    private String name;

    //会议室附件数量
    @NotNull(message = "附件数量不能为空！")
    private Integer num;
}
