package com.ust.shbay.manager.biz.Meeting.bo;

import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingBookingEditBO extends BaseUser {

    //id
    private Integer id;
    /**
     * 以下为更新预约时传入参数
     */
    //单位名称
    private List<VisitCompany> visitCompanyList;

    //参观人数
    private Integer bookingNumber;

//    //单位类型id
//    private Integer companyTypeId;

    //预约人
    private String bookingPersion;

    //预约人电话
    private String bookingPhone;

    //会议室id
    private Integer meetingId;


}
