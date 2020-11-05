package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.TSetting;
import com.ust.shbay.manager.entity.TSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TSettingMapper {
    long countByExample(TSettingExample example);

    int deleteByExample(TSettingExample example);

    int insert(TSetting record);

    int insertSelective(TSetting record);

    List<TSetting> selectByExample(TSettingExample example);

    int updateByExampleSelective(@Param("record") TSetting record, @Param("example") TSettingExample example);

    int updateByExample(@Param("record") TSetting record, @Param("example") TSettingExample example);

    @Select("select value from t_setting where id = #{id} and del_flag = 1")
    String selectValueById(String id);

    @Select("select id, `module`, `value`, `desc`, del_flag, create_time, create_user, update_time, update_user " +
            "from t_setting where id = #{id} and del_flag = 1")
    TSetting selectById(String id);

    int updateSelective(@Param("record") TSetting record);
}