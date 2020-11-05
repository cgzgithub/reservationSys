package com.ust.shbay.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class OssConfig implements Serializable {

    private String accessKey;

    private String secretKey;

    private Integer expireDay;

    public String bucket;

//    public String reservation_bucket;
//
//    public String project_bucket;
//
//    public String other_bucket;

    private Boolean changed;

    private String http;

    private String endPoint;

    private Integer zone;
}
