package com.ust.shbay.manager.biz.showroom.vo;

import com.ust.shbay.manager.entity.Showroom;
import com.ust.shbay.manager.entity.vo.showroom.GuidePersonTime;
import com.ust.shbay.manager.entity.vo.showroom.ShowroomBookingNum;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ShowroomBookingAnalysisVO {

    //展厅
    private List<Showroom> showroomList;

    //每个展厅接待次数、取消次数、预约未到访次数
    private Map<String, List<ShowroomBookingNum>> map;

    //各讲解员接待人次
    private List<GuidePersonTime> guidePersonTimeList;

    //每日接待人次
    private List<EverydayPersonTime> everydayPersonTimeList;

    @Data
    public class EverydayPersonTime {

        //x轴，时间
        private List<String> xAxis;

        //Y轴:name,data
        private Map<String, Object> series;

    }
}

