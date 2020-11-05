package com.ust.shbay.manager.api.controller;

import com.github.pagehelper.PageInfo;
import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.api.controller.dto.role.*;
import com.ust.shbay.manager.biz.role.RoleBusiness;
import com.ust.shbay.manager.biz.role.bo.*;
import com.ust.shbay.service.base.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * .
 */
@Api(tags = "角色模块")
@RestController
@RequestMapping("api/role/")
public class RoleController extends BaseController {

    @Autowired
    private RoleBusiness roleBusiness;

    @PostMapping("getRole")
    public ResponseEntity<PageInfo> getRole(@RequestBody RoleQuery roleQuery) {
        RoleQueryBO roleQueryBO = new RoleQueryBO();
        BeanUtils.copyProperties(roleQuery, roleQueryBO);
        setBaseAccount(roleQueryBO);
        PageInfo pageInfo = roleBusiness.getRole(roleQueryBO);
        return ResponseEntity.buildSuccEntity(pageInfo);
    }

    @PostMapping("addRole")
    public ResponseEntity addRole(@Valid @RequestBody RoleAdd roleAdd, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        RoleAddBO roleAddBO = new RoleAddBO();
        BeanUtils.copyProperties(roleAdd, roleAddBO);
        setBaseAccount(roleAddBO);
        roleBusiness.addRole(roleAddBO);
        return ResponseEntity.buildSuccEntity();
    }

    @PostMapping("editRole")
    public ResponseEntity editRole(@Valid @RequestBody RoleEdit roleEdit, BindingResult bindingResult) {
        //校验参数
        if (checkParam(bindingResult) != null) {
            return checkParam(bindingResult);
        }
        RoleEditBO roleEditBO = new RoleEditBO();
        BeanUtils.copyProperties(roleEdit, roleEditBO);
        setBaseAccount(roleEditBO);
        roleBusiness.editRole(roleEditBO);
        return ResponseEntity.buildSuccEntity();
    }

    @PostMapping("deleteRole")
    public ResponseEntity deleteRole(@RequestBody RoleDelete roleDelete) {
        RoleDeleteBO roleDeleteBO = new RoleDeleteBO();
        BeanUtils.copyProperties(roleDelete, roleDeleteBO);
        setBaseAccount(roleDeleteBO);
        roleBusiness.deleteRole(roleDeleteBO);
        return ResponseEntity.buildSuccEntity();
    }

}
