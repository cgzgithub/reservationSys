package com.ust.shbay.manager.biz.Meeting.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 每天的预约情况
 */
@Data
public class RoadshowBookingTimeVO {

    //预约日期
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bookingDate;

    //路演厅id
    private Integer meetingId;

    //路演厅名
    private String meetingName;

    //路演厅最大座位数
    private Integer seatNum;

    //周几
    private String bookingWeek;

    //上、下午预订情况
    private List<RoadshowChildBookingTime> childBookingTimeList;
}
