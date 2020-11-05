package com.ust.shbay.manager.dao;

import com.ust.shbay.manager.entity.ApartmentReview;
import com.ust.shbay.manager.entity.ApartmentReviewExample;
import java.util.List;

import com.ust.shbay.manager.entity.vo.ApartmentReviewHiQueryDO;
import com.ust.shbay.manager.entity.vo.ApartmentReviewVO;
import org.apache.ibatis.annotations.Param;

public interface ApartmentReviewMapper {
    long countByExample(ApartmentReviewExample example);

    int deleteByExample(ApartmentReviewExample example);

    int insert(ApartmentReview record);

    int insertSelective(ApartmentReview record);

    List<ApartmentReview> selectByExample(ApartmentReviewExample example);

    int updateByExampleSelective(@Param("record") ApartmentReview record, @Param("example") ApartmentReviewExample example);

    int updateByExample(@Param("record") ApartmentReview record, @Param("example") ApartmentReviewExample example);

    List<ApartmentReviewVO> selectByCondition(@Param("condition") ApartmentReviewHiQueryDO condition);
}