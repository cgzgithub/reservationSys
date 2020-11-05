package com.ust.shbay.manager.api.controller.dto.showroom;

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
public class ShowroomBookingAdd extends BaseToken {

    //展厅id
    @NotNull(message = "展厅不能为空！")
    private Integer showroomId;

    //单位名称
    @NotNull(message = "单位名称不能为空！")
    private List<VisitCompany> visitCompanyList;

    //预约日期
    @NotNull(message = "预约日期不能为空！")
    private Date visitDate;

    //开始时间
    @NotNull(message = "预约开始时间不能为空！")
    private String beginTime;

    //结束时间
    @NotNull(message = "预约开始时间不能为空！")
    private String endTime;

    //参观人数
    private Integer bookingNumber;

//    //单位类型id
//    @NotNull(message = "单位类型不能为空！")
//    private Integer companyTypeId;

    //预约人
    @NotBlank(message = "预约人不能为空！")
    private String bookingPersion;

    //预约人电话
    @Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")
    @NotBlank(message = "预约人电话不能为空！")
    private String bookingPhone;

    //讲解员
    private Integer guideId;

    //预约通道（0:小程序，1:web端）
    private Integer passageway;

}
