package com.ust.shbay.common;

/**
 *
 */
public class UUID {

    public static String randomUUID() {
        return java.util.UUID.randomUUID().toString().replace("-","");
    }
}
