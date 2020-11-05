package com.ust.shbay.manager.biz.showroom.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 每天的预约情况
 */
@Data
public class BookingTimeVO {

    //展厅id
    private Integer showroomId;

    //展厅名
    private String showroomName;

    //展厅最大接待人数
    private Integer seatNum;

    //预约日期
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bookingDate;

    //周几
    private String bookingWeek;

    //该时间段预定状态
    private List<ChildBookingTime> childBookingTimeList;
}
