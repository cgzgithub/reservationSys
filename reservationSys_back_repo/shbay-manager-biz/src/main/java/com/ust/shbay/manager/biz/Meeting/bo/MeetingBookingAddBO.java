package com.ust.shbay.manager.biz.Meeting.bo;

import com.ust.shbay.manager.entity.MeetingEnclosure;
import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingBookingAddBO extends BaseUser {

    //会议室id
    private  Integer meetingId;

    //会议主题
    private String name;

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

    //预约通道（0:小程序，1:web端）
    private Integer passageway;

    //附件
    private List<MeetingEnclosure> enclosureList;

//    //电脑数量
//    private Integer computerNum;
//
//    //台卡数量
//    private Integer tecaNum;
//
//    //投影数量
//    private Integer projectionNum;
//
//    //手持话筒数量
//    private Integer handMicrophoneNum;
//
//    //鹅颈话筒数量
//    private Integer gooseneckMicrophoneNum;

    //会议室摆放要求图片列表
    private String putRequirementPicId;
}
