package com.ust.shbay.manager.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //log.debug("---------------------开始进入请求地址拦截----------------------------");
//        httpServletRequest.setAttribute(Constant.SESSION_CHECK, true);
//        httpServletRequest.setAttribute(Constant.SESSION_CHANNEL, LoginChannel.MANAGER.name());

        return true;
    }
}