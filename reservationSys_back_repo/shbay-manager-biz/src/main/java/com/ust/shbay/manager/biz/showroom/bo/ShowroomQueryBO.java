package com.ust.shbay.manager.biz.showroom.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShowroomQueryBO extends BaseUser {

    //是否有删除数据
    //true：有删除数据，在展厅管理列表中使用，
    //null或false：没有删除数据，在预约时使用
    private Boolean flag;

    //页码
    private Integer pageNumber;

    //每页数量
    private Integer pageSize;
}