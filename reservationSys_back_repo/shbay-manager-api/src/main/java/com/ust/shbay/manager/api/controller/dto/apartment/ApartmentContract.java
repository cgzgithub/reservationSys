package com.ust.shbay.manager.api.controller.dto.apartment;


import com.ust.shbay.service.base.BaseToken;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class ApartmentContract extends BaseToken {

    private Integer id;

    private Date contractBeginDate;

    private Date contractEndDate;

    //合同状态（0：合同执行中:-1提前解约，-2合同到期）
    private Integer contractStatus;

    //公寓id
    private Integer apartmentId;
}
