package com.ust.shbay.common.exception;

import com.ust.shbay.common.enums.HttpCodeEnum;

/**
 *
 */
public class SException extends RuntimeException {

    private Integer code;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SException (Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public SException (String message) {
        this.code = HttpCodeEnum.SERVER_ERROR.getCode();
        this.message = message;
    }
}
