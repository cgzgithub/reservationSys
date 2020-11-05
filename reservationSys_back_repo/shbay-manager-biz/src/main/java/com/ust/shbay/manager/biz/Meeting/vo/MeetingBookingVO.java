package com.ust.shbay.manager.biz.Meeting.vo;

import com.ust.shbay.manager.entity.MeetingBooking;
import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.manager.entity.vo.TRelationVO;
import lombok.Data;

import java.util.List;

@Data
public class MeetingBookingVO extends MeetingBooking {
    //会议室名字
    private  String meetingName;

//    //单位类型
//    private String companyTypeName;

    //取消原因
    private String cancelReasonName;

    //到访图片
    private List<TRelationVO> tRelationList;

    //摆放要求下载url
    private String putDownloadUrl;

    //是否为路演厅，true路演厅，false会议室
    private Boolean isRoadshow;

    private List<VisitCompany> visitCompanyList;
}
