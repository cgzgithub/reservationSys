package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.VisitCompany;
import com.ust.shbay.manager.entity.VisitCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VisitCompanyMapper {
    long countByExample(VisitCompanyExample example);

    int deleteByExample(VisitCompanyExample example);

    int insert(VisitCompany record);

    int insertSelective(VisitCompany record);

    List<VisitCompany> selectByExample(VisitCompanyExample example);

    int updateByExampleSelective(@Param("record") VisitCompany record, @Param("example") VisitCompanyExample example);

    int updateByExample(@Param("record") VisitCompany record, @Param("example") VisitCompanyExample example);

    @Select("select `name` from t_visit_company where del_flag = 1 and booking_id = #{bookingId} and `type` = #{type}")
    List<String> getNames(@Param("bookingId")Integer bookingId, @Param("type")Integer type);
}