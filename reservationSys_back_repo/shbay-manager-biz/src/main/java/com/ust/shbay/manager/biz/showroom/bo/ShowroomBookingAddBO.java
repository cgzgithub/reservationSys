package com.ust.shbay.manager.biz.showroom.bo;

import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingAddBO extends BaseUser {

    //展厅id
    private Integer showroomId;

    //单位名称
    private List<VisitCompany> visitCompanyList;

    //预约日期
    private Date visitDate;

    //开始时间
    private String beginTime;

    //结束时间
    private String endTime;

    //参观人数
    private Integer bookingNumber;

//    //单位类型id
//    private Integer companyTypeId;

    //预约人
    private String bookingPersion;

    //预约人电话
    private String bookingPhone;

    //讲解员
    private Integer guideId;

    //预约通道（0:小程序，1:web端）
    private Integer passageway;

}