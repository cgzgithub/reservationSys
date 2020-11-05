package com.ust.shbay.manager.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class DictType {
    //id
    private Integer id;

    //删除标记（1：正常，0：删除）
    private Integer delFlag;

    //创建时间
    private Date createTime;

    //创建人
    private String createUser;

    //修改时间
    private Date updateTime;

    //修改人
    private String updateUser;

    //字典名称
    private String title;

    //字典type
    private String type;

    //备注
    private String description;

    //排序值
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;
}
