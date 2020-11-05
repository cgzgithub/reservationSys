package com.ust.shbay.manager.biz.showroom.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomEditBO extends BaseUser {
    private Integer  id;

    //展厅名称
    private String name;

    //位置
    private String position;

    //面积
    private BigDecimal area;

    //最大座位数
    private Integer seatNum;

    //上午开始时间
    private String amBeginTime;

    //上午结束时间
    private String amEndTime;

    //下午开始时间
    private String pmBeginTime;

    //下午结束时间
    private String pmEndTime;
}
