package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.SyAccountRole;
import com.ust.shbay.manager.entity.SyAccountRoleExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SyAccountRoleMapper {
    long countByExample(SyAccountRoleExample example);

    int deleteByExample(SyAccountRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SyAccountRole record);

    int insertSelective(SyAccountRole record);

    List<SyAccountRole> selectByExample(SyAccountRoleExample example);

    SyAccountRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SyAccountRole record, @Param("example") SyAccountRoleExample example);

    int updateByExample(@Param("record") SyAccountRole record, @Param("example") SyAccountRoleExample example);

    int updateByPrimaryKeySelective(SyAccountRole record);

    int updateByPrimaryKey(SyAccountRole record);

}