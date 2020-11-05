package com.ust.shbay.manager.biz.project.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectDelBO extends BaseUser {

    //项目id
    private Integer id;
}
