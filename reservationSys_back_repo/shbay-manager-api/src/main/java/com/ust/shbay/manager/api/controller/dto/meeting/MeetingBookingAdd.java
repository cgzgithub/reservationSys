package com.ust.shbay.manager.api.controller.dto.meeting;

import com.ust.shbay.manager.entity.MeetingEnclosure;
import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingBookingAdd extends BaseToken {

    @NotNull(message = "会议室不能为空！")
    private  Integer meetingId;

    //会议主题
    @NotBlank(message = "会议主题不能为空！")
    private String name;

    //单位名称
    @NotNull(message = "单位名称不能为空！")
    private List<VisitCompany> visitCompanyList;

    //预约日期
    @NotNull(message = "预约日期不能为空！")
    private Date visitDate;

    //开始时间
    @NotBlank(message = "开始时间不能为空！")
    private String beginTime;

    //结束时间
    @NotBlank(message = "结束时间不能为空！")
    private String endTime;

    //参观人数
    private Integer bookingNumber;

//    //单位类型id
//    @NotNull(message = "单位类型不能为空！")
//    private Integer companyTypeId;

    //预约人
    @NotNull(message = "预约人不能为空！")
    private String bookingPersion;

    //预约人电话
    @NotNull(message = "预约人电话不能为空！")
    @Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")
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
