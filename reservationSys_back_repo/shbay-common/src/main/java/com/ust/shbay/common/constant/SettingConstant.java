package com.ust.shbay.common.constant;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;

import javax.print.DocFlavor;

/**
 *
 */
public class SettingConstant {

    public static class QINIU {
        public static class ZONE {
            // 七牛云存储区域 自动判断
            public static final Integer ZONE_AUTO = -1;

            // 七牛云存储区域 华东
            public static final Integer ZONE_ZERO = 0;

            // 七牛云存储区域 华北
            public static final Integer ZONE_ONE = 1;

            // 七牛云存储区域 华南
            public static final Integer ZONE_TWO = 2;

            // 七牛云存储区域 北美
            public static final Integer ZONE_THREE = 3;

            // 七牛云存储区域 东南亚
            public static final Integer ZONE_FOUR = 4;
        }

        public static class DIR {
            // 七牛云存储区域 自动判断
            ////    public String reservation_bucket;
            ////
            ////    public String project_bucket;
            ////
            ////    public String other_bucket;
            // 预定类文件存放
            public static final String RESERVATION = "shbay/reservation/";

            //工程类文件
            public static final String PROJECT = "shbay/project/";

            //其他
            public static final String OTHER = "shbay/other/";

        }
    }

    //设置表module
    public static class SETTING_MODULE {

        //讲解员设置
        public static final String GUIDE = "GUIDE";

        //七牛云
        public static final String QINIU_OSS = "QINIU_OSS";

        //阿里云短信配置
        public static final String ALI_SMS = "ALI_SMS";

    }

    //设置表KEY
    public static class SETTING_ID {

        //默认讲解员设置id
        public static final String DEFAULT_GUIDE = "DEFAULT_GUIDE";

        //七牛云配置
        public static final String QINIU_OSS_BUCKET_PUBLIC = "QINIU_OSS_BUCKET_PUBLIC";
        public static final String QINIU_OSS_BUCKET_PRIVATE = "QINIU_OSS_BUCKET_PRIVATE";
        public static final String QINIU_OSS_PUBLIC_ENDPOINT = "QINIU_OSS_PUBLIC_ENDPOINT";
        public static final String QINIU_OSS_ACCESSKEY = "QINIU_OSS_ACCESSKEY";
        public static final String QINIU_OSS_SECRETKEY = "QINIU_OSS_SECRETKEY";
        public static final String QINIU_OSS_ZONE = "QINIU_OSS_ZONE";
        public static final String QINIU_OSS_HTTP = "QINIU_OSS_HTTP";
        public static final String QINIU_OSS_HTTPS = "QINIU_OSS_HTTPS";
        public static final String QINIU_OSS_ENDPOINT = "QINIU_OSS_ENDPOINT";

        //阿里云短信配置
        public static final String ALI_SMS_ACCESS_KEY_ID = "ALI_SMS_ACCESS_KEY_ID";
        public static final String ALI_SMS_ACCESS_KEY_SECRET = "ALI_SMS_ACCESS_KEY_SECRET";
        public static final String ALI_SMS_TEMPLATE_CODE = "ALI_SMS_TEMPLATE_CODE";
        public static final String ALI_SMS_SIGN_NAME = "ALI_SMS_SIGN_NAME";
        public static final String ALI_SMS_MESS_TEMPLATE_CODE = "ALI_SMS_MESS_TEMPLATE_CODE";

    }
}
