package com.ust.shbay.manager.biz.Apartment.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentReviewPassBo extends BaseUser {
    //申请id
    private  Integer applyId;
    //审核状态
    private  Integer stage;
    //是否通过，0未通过，1通过
    private Integer isPass;
//    //审核人
//    private  String  reviewer;
    //审核意见
    private  String reviewOpinion;
}
