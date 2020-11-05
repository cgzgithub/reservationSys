package com.ust.shbay.manager.biz.user;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.ust.shbay.manager.biz.user.bo.ChangePwdBO;
import com.ust.shbay.manager.biz.user.bo.ForgetPwdBO;
import com.ust.shbay.manager.biz.user.bo.RegisterBO;
import com.ust.shbay.manager.entity.vo.userDto.PcUserLogin;
import com.ust.shbay.manager.entity.vo.userDto.UserLogin;
import com.ust.shbay.service.base.BaseToken;
import com.ust.shbay.service.base.BaseUser;

/**
 * .
 */
public interface AuthenticationService {

    /**
     * 用户登录认证
     *
     * @param userLogin
     * @return
     */
    BaseToken login(UserLogin userLogin);

    /**
     * pc端注册登录
     * @param pcUserLogin
     * @return
     */
    BaseToken pcLogin(PcUserLogin pcUserLogin);

    /**
     * 用户退出
     * @param loginChannel
     * @param baseUser
     */
    void logout(BaseUser baseUser,String loginChannel);

    /**
     * 修改密码
     *
     * @param changePwdBO
     */
    void changePwd(ChangePwdBO changePwdBO);

    /**
     * 发送验证码
     * @param phone
     */
    SendSmsResponse sendSms(String phone);

    /**
     * 小程序注册
     * @param registerBO
     */
    void register(RegisterBO registerBO);

    /**
     * 忘记密码
     * @param forgetPwdBO
     */
    void forgetPwd(ForgetPwdBO forgetPwdBO);
}
