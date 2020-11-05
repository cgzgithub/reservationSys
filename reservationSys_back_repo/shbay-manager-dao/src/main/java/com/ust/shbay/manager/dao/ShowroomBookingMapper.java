package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.ShowroomBooking;
import com.ust.shbay.manager.entity.ShowroomBookingExample;

import java.util.Date;
import java.util.List;

import com.ust.shbay.manager.entity.vo.showroom.GuidePersonTime;
import com.ust.shbay.manager.entity.vo.showroom.ShowroomBookingNum;
import com.ust.shbay.manager.entity.vo.showroom.ReceptionNumAndDate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ShowroomBookingMapper {
    long countByExample(ShowroomBookingExample example);

    int deleteByExample(ShowroomBookingExample example);

    int insert(ShowroomBooking record);

    int insertSelective(ShowroomBooking record);

    List<ShowroomBooking> selectByExample(ShowroomBookingExample example);

    int updateByExampleSelective(@Param("record") ShowroomBooking record, @Param("example") ShowroomBookingExample example);

    int updateByExample(@Param("record") ShowroomBooking record, @Param("example") ShowroomBookingExample example);

    ShowroomBooking selectById(@Param("id") Integer id);

    int update(@Param("record") ShowroomBooking record);

    @Select("select  case " +
            "when booking.status = 0 then '预约中' " +
            "when booking.status = 1 then '到访' " +
            "when booking.status = -1 then '未到访' " +
            "when booking.status = -2 then '取消' " +
            "end status,COUNT(booking.id) as number, room.`name` as 'showroomName' " +
            "from t_showroom_booking booking " +
            "inner join t_showroom room on room.id = booking.showroom_id " +
            "where visit_date between #{beginDate} and #{endDate} " +
            "and booking.del_flag = 1 and room.del_flag = 1 " +
            "group by status,room.`name`")
    List<ShowroomBookingNum> getAllNumListByVisitDate(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    @Select("select guide.name,SUM(booking.actual_num) personTime from t_showroom_booking booking " +
            "inner join t_guide guide on guide.id = booking.guide_id " +
            "where visit_date between #{beginDate} and #{endDate} and " +
            "booking.del_flag = 1 and booking.status = 1 and guide.del_flag = 1 " +
            "group by guide.name")
    List<GuidePersonTime> getGuidePersonTimeListByVisitDate(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<ReceptionNumAndDate> getReceptionNumAndDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                                     @Param("showroomId") Integer showroomId);
}