package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.Guide;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GuideMapper {
    int insertSelective(Guide guide);

    int deleteByPrimaryKey(Integer id);

    Guide selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Guide guide);

    Guide selectByGuideId(Integer id);

    List<Guide> selectALL();

    @Select("select id from t_guide where del_flag = 1")
    List<Integer> selectAllIds();
}
