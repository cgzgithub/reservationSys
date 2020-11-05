package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.Company;
import com.ust.shbay.manager.entity.CompanyExample;
import java.util.List;

import com.ust.shbay.manager.entity.vo.ApartmentTotalAndRemainder;
import org.apache.ibatis.annotations.Param;

public interface CompanyMapper {
    long countByExample(CompanyExample example);

    int deleteByExample(CompanyExample example);

    int insert(Company record);

    int insertSelective(Company record);

    List<Company> selectByExample(CompanyExample example);

    int updateByExampleSelective(@Param("record") Company record, @Param("example") CompanyExample example);

    int updateByExample(@Param("record") Company record, @Param("example") CompanyExample example);

    Company selectById(Integer id);

     String selectCompanyNameById(Integer id);

    void updateById(@Param("record")Company record);


    Company  selectByCreditRecognitionId(String creditRecognitionId);


    List<ApartmentTotalAndRemainder> selectByCompanyName(@Param("companyName")String companyName);
}