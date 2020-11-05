package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.SyAccountSession;
import com.ust.shbay.manager.entity.SyAccountSessionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SyAccountSessionMapper {
    long countByExample(SyAccountSessionExample example);

    int deleteByExample(SyAccountSessionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SyAccountSession record);

    int insertSelective(SyAccountSession record);

    List<SyAccountSession> selectByExample(SyAccountSessionExample example);

    SyAccountSession selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SyAccountSession record, @Param("example") SyAccountSessionExample example);

    int updateByExample(@Param("record") SyAccountSession record, @Param("example") SyAccountSessionExample example);

    int updateByPrimaryKeySelective(SyAccountSession record);

    int updateByPrimaryKey(SyAccountSession record);
}