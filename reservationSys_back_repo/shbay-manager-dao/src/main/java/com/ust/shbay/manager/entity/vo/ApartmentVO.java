package com.ust.shbay.manager.entity.vo;

import com.ust.shbay.manager.entity.Apartment;
import com.ust.shbay.manager.entity.TRelation;
import lombok.Data;

import java.util.List;

@Data
public class ApartmentVO extends Apartment {
    //照片
    private List<TRelationVO> tRelation;

    //户型字典名称
    private String houseTypeName;
}
