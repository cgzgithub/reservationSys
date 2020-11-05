package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.SyRoleMenu;
import com.ust.shbay.manager.entity.SyRoleMenuExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SyRoleMenuMapper {
    long countByExample(SyRoleMenuExample example);

    int deleteByExample(SyRoleMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SyRoleMenu record);

    int insertSelective(SyRoleMenu record);

    List<SyRoleMenu> selectByExample(SyRoleMenuExample example);

    SyRoleMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SyRoleMenu record, @Param("example") SyRoleMenuExample example);

    int updateByExample(@Param("record") SyRoleMenu record, @Param("example") SyRoleMenuExample example);

    int updateByPrimaryKeySelective(SyRoleMenu record);

    int updateByPrimaryKey(SyRoleMenu record);
}