package com.ust.shbay.manager.entity.vo;

import lombok.Data;

@Data
public class ApartmentTotalAndRemainder {
    //公司id
    private Integer id;
    //信用编号
    private  String creditRecognitionId;
    //公司名称
    private  String companyName;
    //可申请总数
    private  Integer totalNumber;
    //已申请总数
    private  Integer count1;
    //剩余可申请
    private Integer remainder;
}
