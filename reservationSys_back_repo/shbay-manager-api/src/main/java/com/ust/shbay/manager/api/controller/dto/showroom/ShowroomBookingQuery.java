package com.ust.shbay.manager.api.controller.dto.showroom;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingQuery extends BaseToken {

    //账号id
    private String accountId;

    //展厅id
    private Integer showroomId;

    //预约日期
    private Date visitDate;

    //预约时间段-开始日期
    private Date beginDate;

    //预约时间段-结束日期
    private Date endDate;

    //状态(-2:取消，-1:未到访,0:预约中,1:到访)
    private Integer status;

    //预约通道（0:小程序，1:web端）
    private Integer passageway;

    //讲解员
    private Integer guideId;

    //预约人
    private String bookingPersion;

    //单位名称
    private String companyName;

    //页码
    private Integer pageNumber;

    //每页数量
    private Integer pageSize;
}
