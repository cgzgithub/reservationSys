package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.StageNodeInfo;
import com.ust.shbay.manager.entity.StageNodeInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StageNodeInfoMapper {
    long countByExample(StageNodeInfoExample example);

    int deleteByExample(StageNodeInfoExample example);

    int insert(StageNodeInfo record);

    int insertSelective(StageNodeInfo record);

    List<StageNodeInfo> selectByExample(StageNodeInfoExample example);

    int updateByExampleSelective(@Param("record") StageNodeInfo record, @Param("example") StageNodeInfoExample example);

    int updateByExample(@Param("record") StageNodeInfo record, @Param("example") StageNodeInfoExample example);

    @Select("select id, project_id, stage_id, stage_node_id, node_name, `type`, person, finish_time, " +
            "del_flag, create_time, create_user, update_time, update_user " +
            "from t_stage_node_info where id = #{id} and del_flag = 1")
    StageNodeInfo selectById(Integer id);

    int updateSelective(@Param("record") StageNodeInfo record);
}