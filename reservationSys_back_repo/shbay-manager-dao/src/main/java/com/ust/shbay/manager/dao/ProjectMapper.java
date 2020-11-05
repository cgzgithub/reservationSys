package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.Project;
import com.ust.shbay.manager.entity.ProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProjectMapper {
    long countByExample(ProjectExample example);

    int deleteByExample(ProjectExample example);

    int insert(Project record);

    int insertSelective(Project record);

    List<Project> selectByExample(ProjectExample example);

    int updateByExampleSelective(@Param("record") Project record, @Param("example") ProjectExample example);

    int updateByExample(@Param("record") Project record, @Param("example") ProjectExample example);

    int updateSelective(@Param("record") Project record);

    @Select("select id, `number`, `name`, project_type, person, phone, begin_time, end_time, stage_id, " +
            "stage_node_id, del_flag, create_time, create_user, update_time, update_user " +
            "from t_project " +
            "where id = #{id}")
    Project selectById(Integer id);

    @Select("select id from t_project where project_type = #{projectType}")
    List<Integer> selectByType(Integer projectType);
}