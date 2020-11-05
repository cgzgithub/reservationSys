package com.ust.shbay.manager.api.controller.dto.meeting;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingRoomAdd extends BaseToken {
    //会议室名称
    @NotBlank(message = "会议室名称不能为空！")
    private String name;

    //会议室类型id，关联数据字典
    @NotNull(message = "会议室类型不能为空！")
    private Integer type;

    //位置
    @NotBlank(message = "位置不能为空！")
    private String position;

    //面积
    @NotNull(message = "面积不能为空！")
    @Column(precision = 10, scale = 2)
    private BigDecimal area;

    //最大座位数
    @NotNull(message = "最大座位数不能为空！")
    private Integer seatNum;

    //上午开始时间
    @NotBlank(message = "上午开始时间不能为空！")
    private String amBeginTime;

    //上午结束时间
    @NotBlank(message = "上午结束时间不能为空！")
    private String amEndTime;

    //下午开始时间
    @NotBlank(message = "下午开始时间不能为空！")
    private String pmBeginTime;

    //下午结束时间
    @NotBlank(message = "下午结束时间不能为空！")
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

    //附件id列表
    private List<Integer> enclosureIds;
}
