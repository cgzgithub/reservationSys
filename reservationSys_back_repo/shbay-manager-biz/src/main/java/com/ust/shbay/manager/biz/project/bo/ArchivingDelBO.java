package com.ust.shbay.manager.biz.project.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ArchivingDelBO extends BaseUser {

    //id
    private Integer id;
}
