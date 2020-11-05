package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.DictType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DictTypeMapper {

    int insertSelective(DictType dictType);

    DictType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictType dictType);

    List<DictType> getDictTypeListByTitle(String title);

    @Select("select id,title,`type`,description,sort_order,del_flag,create_time,create_user,update_time,update_user " +
            "from t_dict_type where `type` = #{type} and del_flag = 1")
    DictType findByType(String type);
}
