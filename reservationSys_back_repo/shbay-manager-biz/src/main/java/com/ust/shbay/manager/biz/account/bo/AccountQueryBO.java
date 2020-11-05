package com.ust.shbay.manager.biz.account.bo;

import com.ust.shbay.service.base.BaseUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * .
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountQueryBO extends BaseUser {

    // 姓名
    private String name;

    // 账号
    private String accountName;

    // 当前页
    private Integer pageNum;

    // 页面数据条目
    private Integer pageSize;
}
