package com.ust.shbay.manager.biz.Apartment.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentDelBo extends BaseUser {
    private Integer id;
}
