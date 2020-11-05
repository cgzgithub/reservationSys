package com.ust.shbay.manager.biz.Apartment.bo;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentEditBO extends BaseUser {
    private Integer id;
    private String number;

    private String addressRidgepole;

    private String addressNumber;

    private String addressRoom;

    private String residentialAreaName;

    private BigDecimal area;

    private Integer houseType;

    private Integer status;

    //房间图片列表
    private List<TRelation> putPicUrls;
}
