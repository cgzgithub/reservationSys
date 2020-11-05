package com.ust.shbay.service.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.enums.HttpCodeEnum;
import com.ust.shbay.common.enums.LoginChannel;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.manager.dao.SyAccountMapper;
import com.ust.shbay.manager.dao.SyAccountSessionMapper;
import com.ust.shbay.manager.entity.SyAccountSession;
import com.ust.shbay.manager.entity.SyAccountSessionExample;
import com.ust.shbay.service.base.BaseToken;
import com.ust.shbay.service.base.BaseUser;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * .
 */
@Slf4j
@Component  // 声明组件
@Aspect     // 声明切面
@EnableAspectJAutoProxy //spring自动切换JDK动态代理和CGLIB
public class SessionAspect {

    // 登录渠道列表
    static List<String> channelList = new ArrayList<>();

    //可匿名访问的URI列表
    static List<String> anonymousUriList = new ArrayList<>();

    //小程序可访问的列表
    static List<String> appUrlList = new ArrayList<>();

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SyAccountSessionMapper syAccountSessionMapper;

    @Autowired
    private SyAccountMapper syAccountMapper;

    /**
     * 在方法执行前进行切面
     */
    @Pointcut("execution(* com.ust.shbay..*.*(..)) && " +
            "(@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void check() {
    }

    //校验匿名访问接口和小程序访问
    private final boolean cheackUri(String uri, List<String> list){
        for (String s : list) {
            //统一小写
            s = new String(s.toLowerCase());
            uri = new String(uri.toLowerCase());

            if (uri.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private final void initRemoteList() {
        //添加渠道
        channelList.add(LoginChannel.MANAGER.name());
        channelList.add(LoginChannel.APP.name());

        //添加匿名访问接口
        anonymousUriList = Arrays.asList("/api/user/login", "/api/user/sendSms", "/api/user/register", "/api/user/forgetPwd"
                , "/api/user/pcLogin");

        //添加小程序访问列表
        appUrlList = Arrays.asList("/api/dict/getDictInfoByType",
                "/api/showroom/booking/cancel", "/api/showroom/booking/query", "/api/showroom/booking/queryTime",
                "/api/showroom/guide/getAllList", "/api/showroom/booking/add", "/api/showroom/getAllShowroomList",
                "/api/meeting/booking/add", "/api/meeting/booking/cancel", "/api/meeting/booking/query",
                "/api/meeting/getAllMeetingRoomByType", "/api/meeting/meetingBooking/queryTime",
                "/api/meeting/roadshowBooking/queryTime", "/api/meeting/booking/timeDecide",
                "/api/account/getAccountDetail", "/api/user/login", "/api/user/sendSms", "/api/user/register",
                "/api/user/changePwd", "/api/user/forgetPwd"
        );
    }

    private SyAccountSession checkSession(String channel, String token) {

        SyAccountSession session = null;

        //从数据库读取;
        SyAccountSessionExample example = new SyAccountSessionExample();
        SyAccountSessionExample.Criteria criteria = example.createCriteria();
        criteria.andSessionCodeEqualTo(token);
//        criteria.andChannelEqualTo(channel);
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<SyAccountSession> accountSessionList = syAccountSessionMapper.selectByExample(example);

        if (accountSessionList.size() <= 0) {
            log.info("INFO: 数据库中找不到该session：" + token);
            return null;
        }

        session = accountSessionList.get(0);

        Date expireDate = session.getExpireTime();
        Date now = new Date();
        if (now.after(expireDate)) {
            log.info("INFO: 会话已过期：{" + token + "}");
            return null;
        }
        DateTime dateTime = DateTime.now().plusDays(1);
        session.setExpireTime(dateTime.toDate());
        syAccountSessionMapper.updateByPrimaryKeySelective(session);

        return session;
    }

    @Before("check()")
    public void before(JoinPoint point){

        initRemoteList();

        //匿名访问资源
        boolean isAnonymousUri = false;
//        Object sessionCheck = request.getAttribute(Constant.SESSION_CHECK);
//        if (sessionCheck == null || !sessionCheck.toString().equals("true")) {
        isAnonymousUri = cheackUri(request.getRequestURI(),anonymousUriList);
        if (isAnonymousUri)
            return;
//        }

        Object object = point.getArgs()[0];
        String token = null;
        if (object instanceof BaseToken) {
            String jsonContent = JSONObject.toJSONString(object);
            BaseToken baseToken = JSONObject.parseObject(jsonContent, BaseToken.class);
            token = baseToken.getToken();
        } else {
            log.debug("在Header中获取token");
            token = request.getHeader(Constant.SESSION_ACCOUNT);
        }

        if (StringUtils.isEmpty(token)) {
            log.info("会话token为空");
            throw new SException(HttpCodeEnum.UNAUTHORIZED.getCode(), HttpCodeEnum.UNAUTHORIZED.getMessage());
        }
        log.info("token is {}", token);

        //String channel = LoginChannel.APP.name(); //"APP"
        String channel = LoginChannel.MANAGER.name(); //"APP"
        Object sessionChannel = request.getHeader(Constant.SESSION_CHANNEL);
        if (sessionChannel != null) {
            channel = sessionChannel.toString();
        }

        //校验app资源权限
        if (channel == LoginChannel.APP.name() && !cheackUri(request.getRequestURI(),appUrlList)) {
        //if (channel == LoginChannel.MANAGER.name() && !appUrlList.contains(requestUrl)) {
            log.info("没有访问权限");
            throw new SException(HttpCodeEnum.FORBIDDEN.getCode(), HttpCodeEnum.FORBIDDEN.getMessage());
        }

        SyAccountSession accountSession = checkSession(channel, token);

        if (accountSession != null) {
            BaseUser baseUser = new BaseUser();
//            BeanUtils.copyProperties(accountSession, baseUser);
            baseUser.setSyAccountId(accountSession.getAccountId());
            //由于账号为手机号，所以由姓名代替
            baseUser.setAccount(syAccountMapper.selectByPrimaryKey(accountSession.getAccountId()).getName());
            // 登录账户
            log.debug("SessionAspect login baseUser:{}", baseUser);
            request.setAttribute(Constant.CURRENT_USER, baseUser);
        } else {
            log.info("无会话记录");
            throw new SException(HttpCodeEnum.UNAUTHORIZED.getCode(), HttpCodeEnum.UNAUTHORIZED.getMessage());
        }
    }
}
