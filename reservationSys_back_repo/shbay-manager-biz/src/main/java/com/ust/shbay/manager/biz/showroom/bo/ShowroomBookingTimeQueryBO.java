package com.ust.shbay.manager.biz.showroom.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingTimeQueryBO extends BaseUser {

    //查询日期
    private Date visitDate;

    //展厅id
    private Integer showroomId;

    //查询天数
    private Integer num;
}
