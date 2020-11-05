package com.ust.shbay.manager.biz.Apartment.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CompanyEditBo extends BaseUser {
    private  Integer id;

    private String creditRecognitionId;

    private String companyName;

    private Integer totalNumber;
}
