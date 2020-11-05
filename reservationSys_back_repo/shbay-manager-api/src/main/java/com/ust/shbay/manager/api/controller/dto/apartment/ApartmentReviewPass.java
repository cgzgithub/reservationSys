package com.ust.shbay.manager.api.controller.dto.apartment;

import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApartmentReviewPass extends BaseToken {

    //申请id
    @NotNull(message = "申请id不能为空！")
    private Integer applyId;

    //审核阶段
    @NotNull(message = "审核阶段不能为空！")
    private Integer stage;

    @NotNull(message = "审核结果不能为空！")
    //是否通过，0未通过，1通过
    private Integer isPass;

//    //审核人
//    private  String  reviewer;

    //审核意见
    private String reviewOpinion;

}
