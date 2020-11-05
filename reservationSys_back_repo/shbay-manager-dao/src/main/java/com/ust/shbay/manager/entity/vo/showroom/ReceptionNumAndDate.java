package com.ust.shbay.manager.entity.vo.showroom;

import lombok.Data;

import java.util.Date;

@Data
public class ReceptionNumAndDate {

    // 日期
    private Date visitDate;

    // 参观人次
    private Integer number;
}
