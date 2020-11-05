package com.ust.shbay.manager.dao;


import com.ust.shbay.manager.entity.TRelation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RelationMapper {

    int insertSelective(TRelation record);

    TRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TRelation record);

    @Select("SELECT id,type,original_id AS 'originalId',file_url AS 'fileUrl',file_name AS 'fileName' FROM t_relation " +
            "WHERE original_id = #{originalId} and type = #{type} and del_flag = 1")
    List<TRelation> selectByOriginalIdAndType(@Param("originalId") Integer originalId,
                                              @Param("type") String type);

    @Select("SELECT id,type,original_id AS 'originalId',file_url AS 'fileUrl',file_name AS 'fileName' FROM t_relation " +
            "WHERE original_id = #{originalId} and type = #{type} and file_url = #{fileUrl} and del_flag = 1 ")
    List<TRelation> selectByOriginalIdAndTypeAndUrl(@Param("originalId") Integer originalId,
                                              @Param("type") String type,@Param("fileUrl")String fileUrl );

    @Select("SELECT file_url AS 'fileUrl' FROM t_relation " +
            "WHERE original_id = #{originalId} and type = #{type} and del_flag = 1")
    List<String> selecturlByOriginalIdAndType(@Param("type") String type,@Param("originalId") Integer originalId);

}