package com.ust.shbay.manager.biz.Apartment.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApartmentContractBO extends BaseUser {

    private Integer id;

    private Date contractBeginDate;

    private Date contractEndDate;

    //合同状态（0：合同执行中:-1提前解约，-2合同到期）
    private Integer contractStatus;

    //公寓id
    private Integer apartmentId;
}
