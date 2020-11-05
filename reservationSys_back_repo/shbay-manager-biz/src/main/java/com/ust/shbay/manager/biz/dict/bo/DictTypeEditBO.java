package com.ust.shbay.manager.biz.dict.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.math.BigDecimal;


@Data
@EqualsAndHashCode(callSuper=false)
public class DictTypeEditBO extends BaseUser {

    //id
    private Integer id;

    //字典名称
    private String title;

//    //字典type
//    private String type;

    //备注
    private String description;

    //排序值
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;
}
