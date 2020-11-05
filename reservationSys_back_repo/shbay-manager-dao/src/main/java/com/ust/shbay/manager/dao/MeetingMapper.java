package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.Meeting;
import com.ust.shbay.manager.entity.MeetingExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("MeetingMapper")
public interface MeetingMapper {
    long countByExample(MeetingExample example);

    int deleteByExample(MeetingExample example);

    int insert(Meeting record);

    int insertSelective(Meeting record);

    List<Meeting> selectByExample(MeetingExample example);

    int updateByExampleSelective(@Param("record") Meeting record, @Param("example") MeetingExample example);

    int updateByExample(@Param("record") Meeting record, @Param("example") MeetingExample example);

    int updateByPrimaryKeySelective(@Param("record") Meeting record);

    Meeting selectMeetingId(@Param("id") Integer id);

    List<Meeting> selectALL();

    @Select("select dict.id from t_meeting meeting inner join t_dict_info dict on dict.id = meeting.type " +
            "and dict.del_flag = 1 and meeting.del_flag = 1 " +
            "where meeting.id = #{id}")
    Integer selectMeetingTypeId(@Param("id") Integer id);

    @Select("select distinct meeting.id, meeting.name, meeting.am_begin_time, meeting.am_end_time, " +
            "meeting.pm_begin_time, meeting.pm_end_time from t_meeting_booking booking " +
            "inner join t_meeting meeting on meeting.id = booking.meeting_id " +
            "where booking.visit_date between #{beginDate} and #{endDate} " +
            "and booking.del_flag = 1 and booking.status <> -2 and meeting.del_flag = 1 ")
    List<Meeting> selectAllByDate(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    @Select("select @cdate := date_add(@cdate, interval - 1 day) as 'visitDate' " +
            "from(select @cdate := date_add(#{endDate}, interval + 1 day) from t_cdate ) t " +
            "where @cdate > #{beginDate}")
    List<Date> selectVisitDateList(@Param("beginDate") String beginDate, @Param("endDate") String endDate);
}
