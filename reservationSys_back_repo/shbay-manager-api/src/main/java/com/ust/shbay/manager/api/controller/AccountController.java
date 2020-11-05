package com.ust.shbay.manager.api.controller;

import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.api.controller.dto.account.*;
import com.ust.shbay.manager.biz.account.AccountBusiness;
import com.ust.shbay.manager.biz.account.bo.*;
import com.ust.shbay.manager.entity.SyAccount;
import com.ust.shbay.manager.entity.vo.AccountVO;
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
 *
 */
@Api(tags = "账号管理")
@RestController
@RequestMapping("api/account/")
public class AccountController extends BaseController {

    @Autowired
    private AccountBusiness accountBusiness;

    @PostMapping("getAccount")
    public ResponseEntity<PageInfo> getAccount(@RequestBody AccountQuery accountQuery) {
        AccountQueryBO accountQueryBO = new AccountQueryBO();
        BeanUtils.copyProperties(accountQuery, accountQueryBO);
        setBaseAccount(accountQueryBO);
        PageInfo<AccountVO> pageInfo = accountBusiness.getAccount(accountQueryBO);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @PostMapping("openAccount")
    public ResponseEntity openAccount(@Valid @RequestBody AccountOpen accountOpen, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        AccountOpenBO accountOpenBO = new AccountOpenBO();
        BeanUtils.copyProperties(accountOpen, accountOpenBO);
        setBaseAccount(accountOpenBO);
        accountBusiness.openAccount(accountOpenBO);
        return ResponseEntity.buildSuccEntity();
    }

    @PostMapping("editAccount")
    public ResponseEntity editAccount(@Valid @RequestBody AccountEdit accountEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        AccountEditBO accountEditBO = new AccountEditBO();
        BeanUtils.copyProperties(accountEdit, accountEditBO);
        setBaseAccount(accountEditBO);
        accountBusiness.editAccount(accountEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @PostMapping("delAccount")
    public ResponseEntity delAccount(@RequestBody AccountDel accountDel) {
        AccountDelBO accountDelBO = new AccountDelBO();
        BeanUtils.copyProperties(accountDel, accountDelBO);
        setBaseAccount(accountDelBO);
        accountBusiness.delAccount(accountDelBO);
        return ResponseEntity.buildSuccEntity();
    }

    @PostMapping("getAccountDetail")
    public ResponseEntity getAccountDetail(@RequestBody BaseToken baseToken) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        SyAccount syAccount = getAccount(baseToken.getToken());
        return ResponseEntity.buildSuccEntity(syAccount);
    }
}
