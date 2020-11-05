package com.ust.shbay.manager.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ApartmentReviewVO {

    //审核id
    private Integer reviewId;

    //审核id
    private Integer applyId;

    //单位名称
    private String companyName;

    //个人姓名
    private String name;

    //手机号
    private String phone;

    //国籍
    private String nationality;

    //证件号
    private String idNumber;

    //租房类型
    private String houseType;

    //申请时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

    //审核时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reviewTime;

    //本阶段审核结果
    private Integer reviewResult;

    //本阶段审核意见
    private String reviewOpinion;

    //当前申请状态
    private Integer applyStatus;

    //审核人
    private String reviewer;

}
