package com.ust.shbay.manager.biz.dict.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.math.BigDecimal;


@Data
@EqualsAndHashCode(callSuper=false)
public class DictInfoEditBO extends BaseUser {

    //id
    private Integer id;

    //数据名称
    private String title;

    //数据值
    private String value;

    //排序值
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    //是否启用 1启用 -1禁用
    private Integer status;

    //备注
    private String description;

//    //所属字典
//    private Integer dictTypeId;
}
