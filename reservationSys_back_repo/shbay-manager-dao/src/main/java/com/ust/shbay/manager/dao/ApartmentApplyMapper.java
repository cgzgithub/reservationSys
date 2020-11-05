package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.ApartmentApply;
import com.ust.shbay.manager.entity.ApartmentApplyExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ApartmentApplyMapper {
    long countByExample(ApartmentApplyExample example);

    int deleteByExample(ApartmentApplyExample example);

    int insert(ApartmentApply record);

    int insertSelective(ApartmentApply record);

    List<ApartmentApply> selectByExample(ApartmentApplyExample example);

    int updateByExampleSelective(@Param("record") ApartmentApply record, @Param("example") ApartmentApplyExample example);

    int updateByExample(@Param("record") ApartmentApply record, @Param("example") ApartmentApplyExample example);

    @Select("select * from t_apartment_apply where id =#{id} and del_flag=1 ")
    ApartmentApply selectById(Integer id);

    void update(@Param("record") ApartmentApply record);

    @Select("SELECT t1.company_origin_id  companyOriginId from  t_apartment_apply t1 where t1.id=#{id}")
    Integer selectCompanyOriginalIdbyId(Integer id);

    //计算已占用名额数量
    @Select("SELECT IFNULL(COUNT(t1.id),0) FROM t_apartment_apply t1 " +
            "WHERE t1.company_origin_id = #{id} " +
            "AND t1.`status`> 1 AND t1.del_flag = 1")
    Integer selectCount(Integer id);

    //利用公司id关联公司名称
    @Select("SELECT t1.company_name companyName FROM  t_company t1 where id=#{id} and del_flag=1")
    String selectCompanyNameByCompanyId(Integer companyId);

    //利用房屋类型关联房屋类型名称
    @Select("SELECT title FROM t_dict_info where id =#{houseType}")
    String selectHouseTypeNamebyHouseType(Integer houseType);
}