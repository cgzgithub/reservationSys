package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.Showroom;
import com.ust.shbay.manager.entity.ShowroomExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ShowroomMapper {
    long countByExample(ShowroomExample example);

    int deleteByExample(ShowroomExample example);

    int insert(Showroom record);

    int insertSelective(Showroom record);

    List<Showroom> selectByExample(ShowroomExample example);

    int updateByExampleSelective(@Param("record") Showroom record, @Param("example") ShowroomExample example);

    int updateByExample(@Param("record") Showroom record, @Param("example") ShowroomExample example);

    @Select("select id, `name`, `position`, area, seat_num, am_begin_time, am_end_time, pm_begin_time, " +
            "pm_end_time, del_flag, create_time, create_user, update_time, update_user " +
            "from t_showroom where id = #{id} and del_flag = 1")
    Showroom selectById(Integer id);

    int updateSelective(@Param("record") Showroom record);

    @Select("select id, `name`, `position`, area, seat_num, am_begin_time, am_end_time, pm_begin_time, " +
            "pm_end_time, del_flag, create_time, create_user, update_time, update_user " +
            "from t_showroom order by update_time desc")
    List<Showroom> selectAll();

    @Select("select id, `name`, `position`, area, seat_num, am_begin_time, am_end_time, pm_begin_time, " +
            "pm_end_time, del_flag, create_time, create_user, update_time, update_user " +
            "from t_showroom where del_flag = 1 order by update_time desc")
    List<Showroom> selectAllByDelFlag();

    @Select("select distinct room.id, room.name from t_showroom_booking booking " +
            "inner join t_showroom room on room.id = booking.showroom_id " +
            "where booking.visit_date between #{beginDate} and #{endDate} and booking.del_flag = 1 and booking.status = 1 " +
            "and room.del_flag = 1")
    List<Showroom> selectAllByDate(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
}