package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.ProjectMaterial;
import com.ust.shbay.manager.entity.ProjectMaterialExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProjectMaterialMapper {
    long countByExample(ProjectMaterialExample example);

    int deleteByExample(ProjectMaterialExample example);

    int insert(ProjectMaterial record);

    int insertSelective(ProjectMaterial record);

    List<ProjectMaterial> selectByExample(ProjectMaterialExample example);

    int updateByExampleSelective(@Param("record") ProjectMaterial record, @Param("example") ProjectMaterialExample example);

    int updateByExample(@Param("record") ProjectMaterial record, @Param("example") ProjectMaterialExample example);

    @Select("select id, project_id, stage_id, stage_node_id, del_flag, create_time, create_user, update_time, update_user " +
            "from t_project_material where id = #{id} and del_flag = 1")
    ProjectMaterial selectById(Integer id);

    int updateSelective(@Param("record") ProjectMaterial record);
}