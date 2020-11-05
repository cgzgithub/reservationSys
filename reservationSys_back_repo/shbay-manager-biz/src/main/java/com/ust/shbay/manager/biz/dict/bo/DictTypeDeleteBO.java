package com.ust.shbay.manager.biz.dict.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DictTypeDeleteBO extends BaseUser {

    //id
    private Integer id;
}
