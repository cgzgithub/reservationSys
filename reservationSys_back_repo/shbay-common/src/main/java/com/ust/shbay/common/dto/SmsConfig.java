package com.ust.shbay.common.dto;

import lombok.Data;

@Data
public class SmsConfig {

    private String accessKey;

    private String secretKey;

    private String templateCode;

    private String signName;

}
