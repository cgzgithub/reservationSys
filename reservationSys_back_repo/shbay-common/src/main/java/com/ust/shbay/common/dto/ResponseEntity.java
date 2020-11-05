package com.ust.shbay.common.dto;

import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.enums.HttpCodeEnum;
import lombok.Data;

/**
 *
 * 前后端交互数据标准
 */
@Data
public class ResponseEntity<T> {

    // 状态 0:失败  1:成功
    private Integer status;

    // 业务数据
    private T data;

    // 描述信息
    private String msg;

    // 错误码
    private Integer code;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    public static ResponseEntity buildSuccEntity(Object data) {
        ResponseEntity entity = new ResponseEntity();
        entity.setData(data);
        entity.setStatus(Constant.Sys.SUCCESS);
        entity.setCode(HttpCodeEnum.OK.getCode());
        entity.setMsg(HttpCodeEnum.OK.getMessage());
        return entity;
    }

    public static ResponseEntity buildSuccEntity() {
        ResponseEntity entity = new ResponseEntity();
        entity.setStatus(Constant.Sys.SUCCESS);
        entity.setCode(HttpCodeEnum.OK.getCode());
        entity.setMsg(HttpCodeEnum.OK.getMessage());
        return entity;
    }

    public static ResponseEntity buildFailedEntity(String msg) {
        ResponseEntity entity = new ResponseEntity();
        entity.setStatus(Constant.Sys.FAILED);
        entity.setCode(HttpCodeEnum.OK.getCode());
        entity.setMsg(msg);
        return entity;
    }

    public static ResponseEntity buildFailedEntity(Integer code, String msg) {
        ResponseEntity entity = new ResponseEntity();
        entity.setStatus(Constant.Sys.FAILED);
        entity.setCode(code);
        entity.setMsg(msg);
        return entity;
    }
}
