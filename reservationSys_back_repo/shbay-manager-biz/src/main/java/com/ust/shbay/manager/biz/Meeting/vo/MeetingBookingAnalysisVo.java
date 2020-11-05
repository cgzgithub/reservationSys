package com.ust.shbay.manager.biz.Meeting.vo;

import com.ust.shbay.manager.entity.Meeting;
import com.ust.shbay.manager.entity.vo.meeting.CompanyTypeAndTimes;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class MeetingBookingAnalysisVo {

    //会议室列表
    private List<Meeting> meetingList;

    //各会议室每日接待人次
    private List<EverydayPersonTime> everydayPersonTimeList;

//    //参会企业类型与次数
//    private List<CompanyTypeAndTimes> companyTypeAndTimesList;

    //各会议室使用情况
    private List<MeetingUse> meetingUseList;


    @Data
    public class EverydayPersonTime {

        //x轴，时间
        private List<String> xAxis;

        //Y轴:name,data
        public Map<String, Object> series;

    }

    @Data
    public class MeetingUse {

        //会议室
        private String meetingName;

        //data(使用时长，空闲开放时长)
        public Map<String, Integer> map;

    }
}