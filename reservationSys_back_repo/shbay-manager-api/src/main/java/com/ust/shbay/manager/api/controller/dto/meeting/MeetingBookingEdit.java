package com.ust.shbay.manager.api.controller.dto.meeting;

import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingBookingEdit extends BaseToken {
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
