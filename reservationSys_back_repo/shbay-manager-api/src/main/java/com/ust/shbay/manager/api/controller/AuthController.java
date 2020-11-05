package com.ust.shbay.manager.api.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.api.controller.dto.account.*;
import com.ust.shbay.manager.biz.user.AuthenticationService;
import com.ust.shbay.manager.biz.user.bo.ChangePwdBO;
import com.ust.shbay.manager.biz.user.bo.ForgetPwdBO;
import com.ust.shbay.manager.biz.user.bo.RegisterBO;
import com.ust.shbay.manager.entity.vo.userDto.PcUserLogin;
import com.ust.shbay.manager.entity.vo.userDto.UserLogin;
import com.ust.shbay.service.base.BaseController;
import com.ust.shbay.service.base.BaseToken;
import com.ust.shbay.service.base.BaseUser;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * .
 */
@Api(tags = "用户认证")
@RestController
@RequestMapping("api/user/")
public class AuthController extends BaseController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity login(@Valid @RequestBody UserLogin userLogin, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        BaseToken baseToken = authenticationService.login(userLogin);
        //request.setAttribute(Constant.CURRENT_USER, baseUser);
        return ResponseEntity.buildSuccEntity(baseToken);
    }

    @PostMapping("pcLogin")
    public ResponseEntity pcLogin(@Valid @RequestBody PcUserLogin pcUserLogin, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        BaseToken baseToken = authenticationService.pcLogin(pcUserLogin);
        return ResponseEntity.buildSuccEntity(baseToken);
    }

    @PostMapping("logout")
    public ResponseEntity logout(@RequestBody LoginChannel loginChannel) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        authenticationService.logout(baseUser, loginChannel.getLoginChannel());
        return ResponseEntity.buildSuccEntity();
    }

    @PostMapping("changePwd")
    public ResponseEntity changePwd(@Valid @RequestBody ChangePwd ChangePwd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ChangePwdBO changePwdBO = new ChangePwdBO();
        BeanUtils.copyProperties(ChangePwd, changePwdBO);
        setBaseAccount(changePwdBO);
        authenticationService.changePwd(changePwdBO);
        return ResponseEntity.buildSuccEntity();
    }

    @PostMapping("forgetPwd")
    public ResponseEntity forgetPwd(@Valid @RequestBody ForgetPwd forgetPwd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        ForgetPwdBO forgetPwdBO = new ForgetPwdBO();
        BeanUtils.copyProperties(forgetPwd, forgetPwdBO);
        setBaseAccount(forgetPwdBO);
        authenticationService.forgetPwd(forgetPwdBO);
        return ResponseEntity.buildSuccEntity();
    }

    @PostMapping("sendSms")
    public ResponseEntity sendSms(@Valid @RequestBody SendSms sendSms, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        SendSmsResponse sendSmsResponse = authenticationService.sendSms(sendSms.getPhone());
        return ResponseEntity.buildSuccEntity(sendSmsResponse);
    }

    @PostMapping("register")
    public ResponseEntity register(@Valid @RequestBody Register register, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        RegisterBO registerBO = new RegisterBO();
        BeanUtils.copyProperties(register, registerBO);
        authenticationService.register(registerBO);
        return ResponseEntity.buildSuccEntity();
    }
}
