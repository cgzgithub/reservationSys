package com.ust.shbay.manager.biz.Meeting.vo;

import lombok.Data;

/**
 * 上、下午预订情况
 */
@Data
public class RoadshowChildBookingTime {

    //上午还是下午
    private String amOrPm;

    //开始时间
    private String BeginTime;

    //结束时间
    private String endTime;

    //预约状态（-1：不可预约，0：可预约，1：已预约）
    private Integer status;

}
