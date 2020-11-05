package com.ust.shbay.manager.biz.showroom.vo;

import lombok.Data;

/**
 * 每个时间段的预约情况
 */
@Data
public class ChildBookingTime {

    //开始时间
    private String beginTime;

    //结束时间
    private String endTime;

    //预约状态（-1：不可预约，0：可预约，1：已预约）
    private Integer status;

}
