package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.StageDict;
import com.ust.shbay.manager.entity.StageDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StageDictMapper {
    long countByExample(StageDictExample example);

    int deleteByExample(StageDictExample example);

    int insert(StageDict record);

    int insertSelective(StageDict record);

    List<StageDict> selectByExample(StageDictExample example);

    int updateByExampleSelective(@Param("record") StageDict record, @Param("example") StageDictExample example);

    int updateByExample(@Param("record") StageDict record, @Param("example") StageDictExample example);

    @Select("select stage_name from t_stage_dict where id = #{id} and del_flag = 1")
    String selectNameById(Integer id);

    @Select("select id, project_type, stage_type, stage_name, sort_value, del_flag, create_time, create_user, " +
            "update_time, update_user from t_stage_dict where project_type = #{projectType} and del_flag = 1 " +
            "order by sort_value asc")
    List<StageDict> selectByProjectType(Integer projectType);

    @Select("select id, project_type, stage_type, stage_name, sort_value, del_flag, create_time, create_user," +
            "update_time, update_user from t_stage_dict where id = #{id} and del_flag = 1")
    StageDict selectById(Integer id);

    @Select("select id from t_stage_dict where project_type = #{projectType} and del_flag = 1 " +
            "order by sort_value asc")
    List<Integer> selectIdsByProjectType(Integer projectType);
}