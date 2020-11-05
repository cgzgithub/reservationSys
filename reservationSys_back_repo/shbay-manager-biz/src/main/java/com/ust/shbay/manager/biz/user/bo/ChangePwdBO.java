package com.ust.shbay.manager.biz.user.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ChangePwdBO extends BaseUser {
//
//    private String oldPwd;

    private String newPwd;
//
//    private String repeatPwd;
}
