package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.ContractNode;
import com.ust.shbay.manager.entity.ContractNodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ContractNodeMapper {
    long countByExample(ContractNodeExample example);

    int deleteByExample(ContractNodeExample example);

    int insert(ContractNode record);

    int insertSelective(ContractNode record);

    List<ContractNode> selectByExample(ContractNodeExample example);

    int updateByExampleSelective(@Param("record") ContractNode record, @Param("example") ContractNodeExample example);

    int updateByExample(@Param("record") ContractNode record, @Param("example") ContractNodeExample example);

    int updateSelective(@Param("record") ContractNode record);

    @Select("select id, contract_id, payment_node, payment_money, payment_time, del_flag, create_time, " +
            "create_user, update_time, update_user " +
            "from t_contract_node " +
            "where id = #{id} and del_flag = 1")
    ContractNode selectById(Integer id);

    @Select("select node.id from t_contract_node node " +
            "inner join t_contract contract on contract.id = node.contract_id " +
            "where node.del_flag = 1 and contract.project_id = #{projectId}")
    List<Integer> selectNodeIdsByProjectId(Integer projectId);
}