package com.ust.shbay.manager.biz.Meeting.bo;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingRoomEditBo extends BaseUser {
    private Integer  id;

    //会议室名称
    private String name;

    //会议室类型id，关联数据字典
    private Integer type;

    //位置
    private String position;

    //面积
    private BigDecimal area;

    //最大座位数
    private Integer seatNum;

    //上午开始时间
    private String amBeginTime;

    //上午结束时间
    private String amEndTime;

    //下午开始时间
    private String pmBeginTime;

    //下午结束时间
    private String pmEndTime;

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
    private List<TRelation> putPicUrls;
}
