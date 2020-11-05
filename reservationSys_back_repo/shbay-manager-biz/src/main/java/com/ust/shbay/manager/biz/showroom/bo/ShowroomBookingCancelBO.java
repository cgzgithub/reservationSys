package com.ust.shbay.manager.biz.showroom.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingCancelBO extends BaseUser {

    //id
    private Integer id;

    //取消通道
    private Integer cancelChannel;

    //取消原因字典id
    private Integer cancelReasonId;
}
