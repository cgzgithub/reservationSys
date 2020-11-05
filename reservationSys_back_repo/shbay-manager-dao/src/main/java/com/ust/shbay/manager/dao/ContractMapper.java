package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.Contract;
import com.ust.shbay.manager.entity.ContractExample;
import java.util.List;

import com.ust.shbay.manager.entity.vo.ContractQueryDO;
import com.ust.shbay.manager.entity.vo.ContractVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ContractMapper {
    long countByExample(ContractExample example);

    int deleteByExample(ContractExample example);

    int insert(Contract record);

    int insertSelective(Contract record);

    List<Contract> selectByExample(ContractExample example);

    int updateByExampleSelective(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateByExample(@Param("record") Contract record, @Param("example") ContractExample example);

    int updateSelective(@Param("record") Contract record);

    @Select("select id, project_id, contract_num, contract_name, contract_type, first_party, second_party, sign_time, " +
            "sign_person, begin_time, end_time, `status`, total_money, final_money, del_flag, create_time, " +
            "create_user, update_time, update_user " +
            "from t_contract " +
            "where id = #{id} and del_flag = 1")
    Contract selectById(Integer id);

    List<ContractVO> selectByCondition(@Param("condition")ContractQueryDO contractQueryDO);
}