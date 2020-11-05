package com.ust.shbay.manager.api.controller.dto.showroom;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomAdd extends BaseToken {
    //展厅名称
    @NotBlank(message = "展厅名称不能为空！")
    private String name;

    //位置
    @NotBlank(message = "展厅位置不能为空！")
    private String position;

    //面积
    @Column(precision = 10, scale = 2)
    private BigDecimal area;

    //最大座位数
    private Integer seatNum;

    //上午开始时间
    @NotBlank(message = "展厅上午开放时间不能为空！")
    private String amBeginTime;

    //上午结束时间
    @NotBlank(message = "展厅上午关闭时间不能为空！")
    private String amEndTime;

    //下午开始时间
    @NotBlank(message = "展厅下午开放时间不能为空！")
    private String pmBeginTime;

    //下午结束时间
    @NotBlank(message = "展厅下午关闭时间不能为空！")
    private String pmEndTime;
}
