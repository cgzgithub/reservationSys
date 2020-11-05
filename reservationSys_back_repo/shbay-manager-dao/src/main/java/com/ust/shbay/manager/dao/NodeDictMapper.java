package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.NodeDict;
import com.ust.shbay.manager.entity.NodeDictExample;
import java.util.List;

import com.ust.shbay.manager.entity.vo.ProjectDictVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface NodeDictMapper {
    long countByExample(NodeDictExample example);

    int deleteByExample(NodeDictExample example);

    int insert(NodeDict record);

    int insertSelective(NodeDict record);

    List<NodeDict> selectByExample(NodeDictExample example);

    int updateByExampleSelective(@Param("record") NodeDict record, @Param("example") NodeDictExample example);

    int updateByExample(@Param("record") NodeDict record, @Param("example") NodeDictExample example);

    @Select("select node_name from t_node_dict where id = #{id} and del_flag = 1")
    String selectNameById(Integer id);

    @Select("select id, node_name, stage_dict_id, sort_value, del_flag, create_time, create_user, update_time, " +
            "update_user from t_node_dict where stage_dict_id = #{stageDictId} and del_flag = 1 order by sort_value asc")
    List<NodeDict> selectByStageDictId(Integer stageDictId);

    @Select("select id from t_node_dict where stage_dict_id = #{stageDictId} and del_flag = 1 order by sort_value asc")
    List<Integer> selectIdsByStageDictId(Integer stageDictId);

    @Select("select node.id as 'value', node.node_name as 'text' from t_node_dict node " +
            "inner join t_stage_dict stage on node.stage_dict_id = stage.id " +
            "where stage.project_type = #{projectType} and stage.del_flag = 1 and node.del_flag = 1 " +
            "order by stage.sort_value asc, node.sort_value asc")
    List<ProjectDictVO> selectByProjectType(Integer projectType);
}