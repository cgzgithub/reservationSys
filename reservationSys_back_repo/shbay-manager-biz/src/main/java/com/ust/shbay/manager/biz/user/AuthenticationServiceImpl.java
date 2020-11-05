package com.ust.shbay.manager.biz.user;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.ust.shbay.common.UUID;
import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.dto.SmsConfig;
import com.ust.shbay.common.enums.LoginChannel;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.common.constant.MngConstant;
import com.ust.shbay.common.utils.CacheUtil;
import com.ust.shbay.common.utils.EncryptUtil;
import com.ust.shbay.common.utils.SmsUtil;
import com.ust.shbay.manager.biz.account.AccountBusiness;
import com.ust.shbay.manager.biz.user.bo.ForgetPwdBO;
import com.ust.shbay.manager.biz.user.bo.RegisterBO;
import com.ust.shbay.manager.dao.SyRoleMapper;
import com.ust.shbay.manager.entity.*;
import com.ust.shbay.manager.entity.vo.userDto.PcUserLogin;
import com.ust.shbay.manager.entity.vo.userDto.UserLogin;
import com.ust.shbay.service.base.BaseToken;
import com.ust.shbay.manager.biz.user.bo.ChangePwdBO;
import com.ust.shbay.manager.dao.SyAccountMapper;
import com.ust.shbay.service.account.AccountService;
import com.ust.shbay.service.base.BaseUser;
import com.ust.shbay.service.session.SessionService;
import com.ust.shbay.service.setting.SettingService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${defaultPassword}")
    private String defaultPassword;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountBusiness accountBusiness;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SyAccountMapper syAccountMapper;

    @Autowired
    private SyRoleMapper syRoleMapper;

    @Autowired
    private SettingService settingService;

    @Override
    public BaseToken login(UserLogin authorizationUser) {
        String username = authorizationUser.getUsername();
        String password = authorizationUser.getPassword();
        String loginChannel = authorizationUser.getLoginChannel();

        BaseToken baseToken = null;
        String uuid = null;
        SyAccount syAccount = accountService.auth(username, password);
        if (syAccount != null) {
            //判断小程序登录还是PC端登录，小程序全部开放，PC端需要有相应角色
            if (loginChannel.equals(LoginChannel.MANAGER.name())) {
                //查询PC端是否拥有角色
                List<SyRole> list = syRoleMapper.selectByAccountId(syAccount.getId());
                if (list.size() <= 0) {
                    throw new SException("没有分配权限无法查看！");
                }

                //PC端创建会话
                uuid = sessionService.createSession(syAccount, LoginChannel.MANAGER);
            } else {
                //小程序端创建会话
                uuid = sessionService.createSession(syAccount, LoginChannel.APP);
            }

            // 设置用户信息
            baseToken = new BaseToken();
            baseToken.setToken(uuid);
        } else {
            throw new SException(MngConstant.Error.ACCOUNT_INCORRECT_MSG);
        }
        return baseToken;
    }


    /**
     * pc端注册登录
     *
     * @param pcUserLogin
     * @return
     */
    @SneakyThrows
    @Transactional
    @Override
    public BaseToken pcLogin(PcUserLogin pcUserLogin) {
        //加密校验
        String encrypt = EncryptUtil.aesEncrypt(pcUserLogin.getPhone());
        if (!encrypt.equals(pcUserLogin.getVerificationCode())) {
            throw new SException("身份校验失败！");
        }

        //查询是否有账号
        SyAccountExample example = new SyAccountExample();
        SyAccountExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(pcUserLogin.getPhone());
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<SyAccount> syAccountList = syAccountMapper.selectByExample(example);
        BaseToken baseToken = null;
        String uuid = null;
        if (syAccountList != null && syAccountList.size() > 0) {
            //有账号直接登录
            SyAccount syAccount = syAccountList.get(0);
            //查询权限
            List<SyRole> list = syRoleMapper.selectByAccountId(syAccount.getId());
            if (list.size() <= 0) {
                //如果登录账号没有角色（小程序账号），添加默认角色
                String pcDefaultRoleId = getPcDefaultRole();
                List<String> roles = Arrays.asList(pcDefaultRoleId);
                accountBusiness.addAccountRole(roles, syAccount.getId(), syAccount.getAccount());
            }
            //登录
            uuid = sessionService.createSession(syAccount, LoginChannel.MANAGER);
        } else {
            //没有账号，注册账号，添加默认权限
            SyAccount syAccount = addAccount(pcUserLogin.getPhone());
            //登录
            uuid = sessionService.createSession(syAccount, LoginChannel.MANAGER);
        }
        // 设置用户信息
        baseToken = new BaseToken();
        baseToken.setToken(uuid);
        return baseToken;
    }

    //获取PC端默认角色
    private String getPcDefaultRole() {
        SyRoleExample example = new SyRoleExample();
        SyRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRoleTypeEqualTo(Constant.ROLE_TYPE.BACK_DEFAULT);
        List<SyRole> syRoleList = syRoleMapper.selectByExample(example);
        if (syRoleList != null && syRoleList.size() > 0) {
            String roleId = syRoleList.get(0).getId();
            return roleId;
        }
        return null;
    }

    //注册
    private SyAccount addAccount(String account) {
        //添加账号
        SyAccount syAccount = new SyAccount();
        String uuid = UUID.randomUUID().replace("-", "");
        syAccount.setId(uuid);
        syAccount.setAccount(account);
        syAccount.setName(account);
        String md5Pwd = DigestUtils.md5DigestAsHex(defaultPassword.getBytes());
        syAccount.setPassword(md5Pwd);
        syAccountMapper.insertSelective(syAccount);

        //添加账号角色
        String pcDefaultRoleId = getPcDefaultRole();
        List<String> roles = Arrays.asList(pcDefaultRoleId);
        accountBusiness.addAccountRole(roles, syAccount.getId(), null);
        return syAccount;
    }

    @Override
    public void logout(BaseUser baseUser, String loginChannel) {
        String accountId = baseUser.getSyAccountId();
        SyAccount syAccount = syAccountMapper.selectByPrimaryKey(accountId);
        if (syAccount != null) {
            if (loginChannel.equals(LoginChannel.MANAGER.name())) {
                sessionService.deleteSession(syAccount, LoginChannel.MANAGER);
            } else {
                sessionService.deleteSession(syAccount, LoginChannel.APP);
            }
        }
    }

    @Override
    public void changePwd(ChangePwdBO changePwdBO) {
        String accountId = changePwdBO.getSyAccountId();
        SyAccount syAccount = syAccountMapper.selectByPrimaryKey(accountId);
        if (syAccount != null) {
//            String newPwd = changePwdBO.getNewPwd();
//            String repeatPwd = changePwdBO.getRepeatPwd();
//            if (!newPwd.equals(repeatPwd)) {
//                throw new SException(MngConstant.Error.REPEAT_PWD_NOT_SAME_MSG);
//            }
//            String md5Pwd = DigestUtils.md5DigestAsHex(changePwdBO.getOldPwd().getBytes());
//            if (!syAccount.getPassword().equals(md5Pwd)) {
//                throw new SException(MngConstant.Error.PWD_NOT_INCORRECT_MSG);
//            }
            SyAccount entity = new SyAccount();
            entity.setId(syAccount.getId());
            entity.setPassword(DigestUtils.md5DigestAsHex(changePwdBO.getNewPwd().getBytes()));
            syAccountMapper.updateByPrimaryKeySelective(entity);
        }
    }


    /**
     * 发送验证码
     *
     * @param phone
     */
    @Override
    public SendSmsResponse sendSms(String phone) {

        SendSmsResponse sendSmsResponse = new SendSmsResponse();
        //查询短信配置
        SmsConfig smsConfig = settingService.getSmsConf("code");

        //生成验证码
        SmsUtil smsUtil = new SmsUtil();
        String code = smsUtil.getCode();
        try {
            //发送验证码
            sendSmsResponse = smsUtil.sendSms(phone, smsConfig, code);

            //删除过期验证码
            CacheUtil cacheUtil = new CacheUtil();
            cacheUtil.clearCache();

            //添加新验证码
            cacheUtil.addCache(phone, code);

        } catch (Exception e) {
            throw new SException(sendSmsResponse.getMessage());
        }
        return sendSmsResponse;
    }

    /**
     * 小程序注册
     *
     * @param registerBO
     */
    @Override
    public void register(RegisterBO registerBO) {
        //校验验证码
        if (registerBO.getCode() == null) {
            throw new SException("验证码不能为空！");
        }
        CacheUtil cacheUtil = new CacheUtil();
        String code = cacheUtil.getCache(registerBO.getAccountName());
        if (code == null) {
            throw new SException("验证码不存在！");
        }
        if (!registerBO.getCode().equals(code)) {
            throw new SException("验证码错误！");
        }

        //查询（账号）手机号是否存在
        SyAccountExample example = new SyAccountExample();
        SyAccountExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(registerBO.getAccountName());
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<SyAccount> syAccountList = syAccountMapper.selectByExample(example);
        if (syAccountList.size() > 0) {
            throw new SException("账号已经存在，无法重复注册！");
        }

        //添加账号
        SyAccount syAccount = new SyAccount();
        String uuid = UUID.randomUUID().replace("-", "");
        syAccount.setId(uuid);
        syAccount.setAccount(registerBO.getAccountName());
        syAccount.setName(registerBO.getName());
        if (registerBO.getPassword() == null) {
            throw new SException("注册密码不能为空！");
        }
        String md5Pwd = DigestUtils.md5DigestAsHex(registerBO.getPassword().getBytes());
        syAccount.setPassword(md5Pwd);
        syAccountMapper.insertSelective(syAccount);

        //删除验证码
        cacheUtil.delCacheByKey(registerBO.getAccountName());
    }

    /**
     * 忘记密码
     *
     * @param forgetPwdBO
     */
    @Override
    public void forgetPwd(ForgetPwdBO forgetPwdBO) {
        //校验验证码
        if (forgetPwdBO.getCode() == null) {
            throw new SException("验证码不能为空！");
        }
        CacheUtil cacheUtil = new CacheUtil();
        String code = cacheUtil.getCache(forgetPwdBO.getAccount());
        if (code == null) {
            throw new SException("验证码不存在！");
        }
        if (!forgetPwdBO.getCode().equals(code)) {
            throw new SException("验证码错误！");
        }

        //查询（账号）手机号是否存在
        SyAccountExample example = new SyAccountExample();
        SyAccountExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(forgetPwdBO.getAccount());
        criteria.andDelFlagEqualTo(Constant.Status.NORMAL);
        List<SyAccount> syAccountList = syAccountMapper.selectByExample(example);
        if (syAccountList.size() <= 0) {
            throw new SException("账号不存在！");
        }
        {
            //修改密码
            SyAccount syAccount = syAccountList.get(0);
            String md5Pwd = DigestUtils.md5DigestAsHex(forgetPwdBO.getNewPwd().getBytes());
            syAccount.setPassword(md5Pwd);
            syAccountMapper.insertSelective(syAccount);

            //删除验证码
            cacheUtil.delCacheByKey(forgetPwdBO.getAccount());
        }
    }

}
