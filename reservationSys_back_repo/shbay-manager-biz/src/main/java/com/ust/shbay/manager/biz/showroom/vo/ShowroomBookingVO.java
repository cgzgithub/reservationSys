package com.ust.shbay.manager.biz.showroom.vo;

import com.ust.shbay.manager.entity.ShowroomBooking;
import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.manager.entity.vo.TRelationVO;
import lombok.Data;

import java.util.List;

@Data
public class ShowroomBookingVO extends ShowroomBooking {

    //展厅名称
    private String showroomName;

//    //单位类型
//    private String companyTypeName;

    //讲解员姓名
    private String guideName;

    //取消原因
    private String cancelReasonName;

    //到访图片
    private List<TRelationVO> tRelationList;

    private List<VisitCompany> visitCompanyList;
}
