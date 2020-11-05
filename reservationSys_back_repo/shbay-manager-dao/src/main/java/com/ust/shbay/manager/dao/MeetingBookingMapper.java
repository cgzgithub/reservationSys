package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.MeetingBooking;
import com.ust.shbay.manager.entity.MeetingBookingExample;

import java.util.Date;
import java.util.List;

import com.ust.shbay.manager.entity.vo.meeting.CompanyTypeAndTimes;
import com.ust.shbay.manager.entity.vo.showroom.ReceptionNumAndDate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MeetingBookingMapper {
    long countByExample(MeetingBookingExample example);

    int deleteByExample(MeetingBookingExample example);

    int insert(MeetingBooking record);

    int insertSelective(MeetingBooking record);

    List<MeetingBooking> selectByExample(MeetingBookingExample example);

    int updateByExampleSelective(@Param("record") MeetingBooking record, @Param("example") MeetingBookingExample example);

    int updateByExample(@Param("record") MeetingBooking record, @Param("example") MeetingBookingExample example);

    MeetingBooking selectById(@Param("id") Integer id);

    int update(@Param("record")  MeetingBooking record);

//    @Select("select dict.title companyType, count(booking.id) times from t_meeting_booking booking "+
//            "inner join t_dict_info dict on dict.id = booking.company_type_id " +
//            "where booking.visit_date between #{beginDate} and #{endDate} and "+
//            "booking.del_flag = 1 and booking.`status` <> -2 and dict.del_flag = 1 "+
//            "group by companyType ")
//    List<CompanyTypeAndTimes> getCompanyTypeAndTimes(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<ReceptionNumAndDate> getReceptionNumAndDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate,
                                                     @Param("meetingId") Integer meetingId);

    @Select("select booking.begin_time as 'beginTime', booking.end_time as 'endTime' from t_meeting_booking booking " +
            "inner join t_meeting meeting on meeting.id = booking.meeting_id " +
            "where booking.visit_date between #{beginDate} and #{endDate} " +
            "and meeting.id = #{meetingId} and booking.del_flag = 1 and booking.`status`<> -2 and meeting.del_flag = 1")
    List<MeetingBooking> getMeetingBookingTime(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                         @Param("meetingId") Integer meetingId);
}