package com.ust.shbay.common.utils;

import com.ust.shbay.common.exception.SException;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.*;

/**
 * 缓存验证码
 */
public class CacheUtil {

    @Data
    class CodeEntity {
        //验证码
        private String code;
        //过期时间
        private Date expireTime;
    }

    /**
     * 存储缓存数据,key为电话号码，value为Code
     */
    public static Map<String, CodeEntity> map;

    private static final Integer MINUTE = 5;

    /**
     * 添加单个数据
     */
    public synchronized void addCache(String key, String code) {
        if (map == null) {
            map = new HashMap<>();
        }
        CodeEntity codeEntity = new CodeEntity();
        codeEntity.setCode(code);
        DateTime dateTime = DateTime.now().plusMinutes(MINUTE);
        codeEntity.setExpireTime(dateTime.toDate());
        map.put(key, codeEntity);
    }

    /**
     * 获取缓存数据
     */
    public synchronized String getCache(String key) {
        if (map != null) {
            CodeEntity codeEntity = map.get(key);
            if(codeEntity == null || codeEntity.getCode() == null || codeEntity.getExpireTime() == null){
                return null;
            }
            if (codeEntity.getExpireTime().before(new Date())) {
                throw new SException("验证码已过期请重新发送！");
            } else {
                return codeEntity.getCode();
            }
        }
        return null;
    }

    /**
     * 清理过期数据
     */
    public synchronized void clearCache() {
        if (map != null) {
            //遍历map看是否过期
            Iterator<Map.Entry<String, CodeEntity>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, CodeEntity> entry = it.next();
                //获取过期时间
                if (entry.getValue().getExpireTime().before(new Date())) {
                    it.remove();
                }
            }
        }
    }

    /**
     * 删除单个缓存数据
     */
    public synchronized void delCacheByKey(String key) {
        if (map != null) {
            map.remove(key);
        }
    }
}
