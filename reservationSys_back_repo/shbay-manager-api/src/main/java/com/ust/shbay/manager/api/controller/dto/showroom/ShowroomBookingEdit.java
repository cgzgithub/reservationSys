package com.ust.shbay.manager.api.controller.dto.showroom;

import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingEdit extends BaseToken {
    //id
    private Integer id;

    /**
     * 以下为更新预约时传入参数
     */
    //展厅名称
    private Integer showroomId;

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

    //讲解员
    private Integer guideId;

}
