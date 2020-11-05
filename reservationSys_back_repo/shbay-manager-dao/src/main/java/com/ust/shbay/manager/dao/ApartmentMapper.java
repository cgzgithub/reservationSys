package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.Apartment;
import com.ust.shbay.manager.entity.ApartmentExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("ApartmentMapper")
public interface ApartmentMapper {
    long countByExample(ApartmentExample example);

    int deleteByExample(ApartmentExample example);

    int insert(Apartment record);

    int insertSelective(Apartment record);

    List<Apartment> selectByExample(ApartmentExample example);

    int updateByExampleSelective(@Param("record") Apartment record, @Param("example") ApartmentExample example);

    int updateByExample(@Param("record") Apartment record, @Param("example") ApartmentExample example);

    Apartment selectById(Integer apartmentid);

    int updateByPrimaryKeySelective(@Param("record")Apartment record);

    List<Apartment> selectALL();
}