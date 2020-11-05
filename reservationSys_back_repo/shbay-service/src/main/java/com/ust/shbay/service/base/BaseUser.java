package com.ust.shbay.service.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Component
public class BaseUser {

    // 登录账户ID
    private String syAccountId;

    // 姓名
    private String account;
    public String getSyAccountId() { return syAccountId; }

    public String getAccount() { return account; }

}
