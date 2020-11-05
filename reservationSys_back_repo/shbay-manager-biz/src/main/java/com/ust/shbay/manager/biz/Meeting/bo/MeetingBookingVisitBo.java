package com.ust.shbay.manager.biz.Meeting.bo;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingBookingVisitBo extends BaseUser {
    //id
    private Integer id;

    //实到人数
    private Integer actualNum;

    //评价
    private Integer appraise;

    //实到图片列表，做多三张
    private List<TRelation> imgUrlList;
}
