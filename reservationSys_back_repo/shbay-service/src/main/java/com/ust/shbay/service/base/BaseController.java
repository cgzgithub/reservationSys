
package com.ust.shbay.service.base;

import com.ust.shbay.common.constant.Constant;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.common.enums.HttpCodeEnum;
import com.ust.shbay.common.enums.LoginChannel;
import com.ust.shbay.common.exception.SException;
import com.ust.shbay.manager.entity.SyAccount;
import com.ust.shbay.service.session.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
@Slf4j
public abstract class BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SessionService sessionService;

    protected ResponseEntity checkParam(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseEntity responseEntity = new ResponseEntity();

            List<ObjectError> errors = bindingResult.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                responseEntity.setStatus(Constant.Sys.FAILED);
                responseEntity.setCode(HttpCodeEnum.FAIL.getCode());
                responseEntity.setMsg(fieldError.getDefaultMessage());
                return responseEntity;
            }
        }
        return null;
    }

    protected void setBaseAccount(Object target) {

        BaseUser baseUser = (BaseUser) request.getAttribute(Constant.CURRENT_USER);
        if (baseUser != null) {
            BeanUtils.copyProperties(baseUser, target);
        } else {
            throw new SException(HttpCodeEnum.FAIL.getCode(), HttpCodeEnum.FAIL.getMessage());
        }

    }

    public BaseUser setBaseAccount() {
        //
        BaseUser baseUser = (BaseUser) request.getAttribute(Constant.CURRENT_USER);
        //request.removeAttribute(Constant.SESSION_ACCOUNT);
        //request.removeAttribute(Constant.SESSION_CHANNEL);
        return baseUser;
    }

    protected SyAccount getAccount(String sessionCode) {
        if (StringUtils.isEmpty(sessionCode)) {
            return null;
        }

        Object obj = request.getAttribute(Constant.SESSION_CHANNEL);

        return sessionService.getAccountBySession(sessionCode, (obj != null) ? LoginChannel.valueOf(obj.toString()) : LoginChannel.APP);
    }

}
