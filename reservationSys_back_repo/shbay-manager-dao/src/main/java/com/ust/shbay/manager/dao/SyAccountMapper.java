package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.SyAccount;
import com.ust.shbay.manager.entity.SyAccountExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("SyAccountMapper")
public interface SyAccountMapper {
    long countByExample(SyAccountExample example);

    int deleteByExample(SyAccountExample example);

    int deleteByPrimaryKey(String id);

    int insert(SyAccount record);

    int insertSelective(SyAccount record);

    List<SyAccount> selectByExample(SyAccountExample example);

    SyAccount selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SyAccount record, @Param("example") SyAccountExample example);

    int updateByExample(@Param("record") SyAccount record, @Param("example") SyAccountExample example);

    int updateByPrimaryKeySelective(SyAccount record);

    int updateByPrimaryKey(SyAccount record);
}