package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.MeetingEnclosure;
import com.ust.shbay.manager.entity.MeetingEnclosureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MeetingEnclosureMapper {
    long countByExample(MeetingEnclosureExample example);

    int deleteByExample(MeetingEnclosureExample example);

    int insert(MeetingEnclosure record);

    int insertSelective(MeetingEnclosure record);

    List<MeetingEnclosure> selectByExample(MeetingEnclosureExample example);

    int updateByExampleSelective(@Param("record") MeetingEnclosure record, @Param("example") MeetingEnclosureExample example);

    int updateByExample(@Param("record") MeetingEnclosure record, @Param("example") MeetingEnclosureExample example);

    @Select("select id, meeting_id, name, num from t_meeting_enclosure where id = #{id} and del_flag = 1")
    MeetingEnclosure selectById(Integer id);

    @Select("select id, meeting_id, name, num from t_meeting_enclosure where meeting_id = #{meetingId} and del_flag = 1")
    List<MeetingEnclosure> selectByMeetingId(Integer meetingId);

    int updateSelective(@Param("record")MeetingEnclosure record);

    @Select("select id from t_meeting_enclosure where meeting_id = #{meetingId} and del_flag = 1")
    List<Integer> selectIds(Integer meetingId);
}