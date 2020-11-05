package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.DictInfo;
import com.ust.shbay.manager.entity.DictInfoExample;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DictInfoMapper {

    int insertSelective(DictInfo dictInfo);

    DictInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictInfo dictInfo);

    List<DictInfo> selectByExample(DictInfoExample example);

    @Select("select title from t_dict_info where del_flag=1 and id=#{id}")
    String selectTitleById(Integer id);

    @Select("select id from t_dict_info where del_flag = 1 and dict_type_id = #{typeId}")
    List<Integer> selectIdsByTypeId(Integer typeId);
}
