package com.ust.shbay.manager.biz.showroom.bo;

import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingEditBO extends BaseUser {

    //id
    private Integer id;

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
