package com.ust.shbay.manager.biz.dict.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DictInfoQueryBO extends BaseUser {

    //数据名称
    private String title;

    //是否启用 1启用 -1禁用
    private Integer status;

    //所属字典
    private Integer dictTypeId;

    //页码
    private Integer pageNumber;

    //每页数量
    private Integer pageSize;

    //排序字段
    private String sort;

    //正序asc，倒序desc
    private String order;
}
