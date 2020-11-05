package com.ust.shbay.manager.api.controller.dto.showroom;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingCancel extends BaseToken {
    //id
    @NotNull(message = "id不能为空！")
    private Integer id;

    //取消通道
    private Integer cancelChannel;

    //取消原因字典id
    @NotNull(message = "取消原因不能为空！")
    private Integer cancelReasonId;

}
