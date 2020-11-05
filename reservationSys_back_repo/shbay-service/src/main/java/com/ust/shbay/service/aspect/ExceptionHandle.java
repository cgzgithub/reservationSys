package com.ust.shbay.service.aspect;

import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.common.enums.HttpCodeEnum;
import com.ust.shbay.common.exception.SException ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity handle(Exception e) {
        logger.error("occur exception:", e);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setStatus(Constant.Sys.FAILED);
        if (e instanceof SException ) {
            SException  SException  = (SException ) e;
            responseEntity.setCode(SException .getCode());
            responseEntity.setMsg(SException .getMessage());
        } else {
            responseEntity.setCode(HttpCodeEnum.SERVER_ERROR.getCode());
            responseEntity.setMsg(HttpCodeEnum.SERVER_ERROR.getMessage());
        }
        return responseEntity;
    }
}
