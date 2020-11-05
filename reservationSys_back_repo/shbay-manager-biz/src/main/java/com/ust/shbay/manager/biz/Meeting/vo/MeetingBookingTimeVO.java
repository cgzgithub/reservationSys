package com.ust.shbay.manager.biz.Meeting.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ust.shbay.manager.biz.showroom.vo.ChildBookingTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 每日会议室预订情况
 */
@Data
public class MeetingBookingTimeVO {

    //预约日期
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bookingDate;

    //会议室id
    private Integer meetingId;

    //会议室名
    private String meetingName;

    //会议室最大座位数
    private Integer seatNum;

    //周几
    private String bookingWeek;

    //该时间段预定状态
    private List<ChildBookingTime> childBookingTimeList;
}