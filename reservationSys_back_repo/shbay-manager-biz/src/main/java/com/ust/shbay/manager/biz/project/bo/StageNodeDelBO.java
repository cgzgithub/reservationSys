package com.ust.shbay.manager.biz.project.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class StageNodeDelBO extends BaseUser {

    //记录id
    private Integer id;
}
