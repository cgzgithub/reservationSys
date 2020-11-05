package com.ust.shbay.manager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class DictInfo {
    //id
    private Integer id;

    //删除标记（1：正常，0：删除）
    private Integer delFlag;

    //创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //创建人
    private String createUser;

    //修改时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    //修改人
    private String updateUser;

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

    //所属字典
    private Integer dictTypeId;

    //是否为系统字典，系统字典无法删除，否则会导致异常
    private Integer isSys;
}
