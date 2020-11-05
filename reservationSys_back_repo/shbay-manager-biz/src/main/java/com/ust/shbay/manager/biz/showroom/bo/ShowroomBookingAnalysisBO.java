package com.ust.shbay.manager.biz.showroom.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingAnalysisBO extends BaseUser {

    //开始日期
    private Date beginDate;

    //结束日期
    private Date endDate;
}
