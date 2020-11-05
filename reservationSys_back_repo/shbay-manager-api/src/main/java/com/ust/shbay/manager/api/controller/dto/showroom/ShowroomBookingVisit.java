package com.ust.shbay.manager.api.controller.dto.showroom;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomBookingVisit extends BaseToken {

    //id
    @NotNull(message = "id不能为空！")
    private Integer id;

    //实到人数
    @NotNull(message = "实到人数不能为空！")
    private Integer actualNum;

    //评价
    @NotNull(message = "评价不能为空！")
    private Integer appraise;

    //实到图片列表，做多三张
    private List<TRelation> imgUrlList;

}
