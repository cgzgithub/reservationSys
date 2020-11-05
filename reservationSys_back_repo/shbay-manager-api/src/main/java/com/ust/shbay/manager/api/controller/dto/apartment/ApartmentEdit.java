package com.ust.shbay.manager.api.controller.dto.apartment;

import com.ust.shbay.manager.entity.TRelation;
import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApartmentEdit extends BaseToken {

    @NotNull(message = "id不能为空！")
    private Integer id;

    @NotBlank(message = "编号不能为空！")
    private String number;

    @NotBlank(message = "详细地址不能为空！")
    private String addressRidgepole;

    @NotBlank(message = "详细地址不能为空！")
    private String addressNumber;

    @NotBlank(message = "详细地址不能为空！")
    private String addressRoom;

    @NotBlank(message = "小区名称不能为空！")
    private String residentialAreaName;

    @NotNull(message = "面积不能为空！")
    @Column(precision = 10, scale = 2)
    private BigDecimal area;

    @NotNull(message = "户型不能为空！")
    private Integer houseType;

    @NotNull(message = "状态不能为空！")
    private Integer status;

    //房间图片列表
    private List<TRelation> putPicUrls;
}

