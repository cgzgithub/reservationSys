package com.ust.shbay.manager.biz.Meeting.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class TimeDecideBO extends BaseUser {

    //预约日期
    private Date visitDate;

    //开始时间
    private String beginTime;

}
