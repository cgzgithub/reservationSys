package com.ust.shbay.service.setting;

import com.ust.shbay.common.dto.SmsConfig;

/**
 *
 */
public interface SettingService {

    /**
     * 获取阿里云短信配置
     * @return
     */
    SmsConfig getSmsConf(String type);

}
