package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.SyRole;
import com.ust.shbay.manager.entity.SyRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SyRoleMapper {
    long countByExample(SyRoleExample example);

    int deleteByExample(SyRoleExample example);

    int insert(SyRole record);

    int insertSelective(SyRole record);

    List<SyRole> selectByExample(SyRoleExample example);

    SyRole selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SyRole record, @Param("example") SyRoleExample example);

    int updateByExample(@Param("record") SyRole record, @Param("example") SyRoleExample example);

    int updateByPrimaryKeySelective(@Param("record")SyRole record);

    @Select("select distinct role.id, role.role_name from sy_role role " +
            "inner join sy_account_role sar on role.id = sar.role_id " +
            "inner join sy_account account on account.id = sar.account_id " +
            "where role.del_flag = 1 and account.del_flag = 1 and sar.del_flag = 1 " +
            "and account.id = #{accountId}")
    List<SyRole> selectByAccountId(@Param("accountId")String accountId);

    @Select("select distinct role.id from sy_role role " +
            "inner join sy_account_role sar on role.id = sar.role_id " +
            "inner join sy_account account on account.id = sar.account_id " +
            "where role.del_flag = 1 and account.del_flag = 1 and sar.del_flag = 1 " +
            "and account.id = #{accountId}")
    List<String> selectRoleIdsByAccountId(@Param("accountId")String accountId);

    @Select("select distinct account.id from sy_account account " +
            "inner join sy_account_role sar on account.id = sar.account_id " +
            "inner join sy_role role on role.id = sar.role_id " +
            "where role.del_flag = 1 and account.del_flag = 1 and sar.del_flag = 1 " +
            "and role.id = #{roleId}")
    List<String> selectAccountIdsByRoleId(@Param("roleId")String roleId);
}