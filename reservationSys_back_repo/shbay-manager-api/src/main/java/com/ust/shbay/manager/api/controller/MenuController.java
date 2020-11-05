package com.ust.shbay.manager.api.controller;

import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.biz.menu.MenuBusiness;
import com.ust.shbay.manager.entity.vo.HomePageMenuVO;
import com.ust.shbay.service.menu.vo.Menu;
import com.ust.shbay.service.base.BaseController;
import com.ust.shbay.service.base.BaseToken;
import com.ust.shbay.service.base.BaseUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@Api(tags = "菜单模块")
@RestController
@RequestMapping("api/menu/")
public class MenuController extends BaseController {

    @Autowired
    private MenuBusiness menuBusiness;

    @PostMapping("getMenu")
    public ResponseEntity<List<Menu>> getMenu(@RequestBody BaseToken baseToken) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<Menu> menuList = menuBusiness.getAllMenu(baseUser);
        return ResponseEntity.buildSuccEntity(menuList);
    }

    @PostMapping("getUserMenu")
    public ResponseEntity<List<Menu>> getUserMenu(@RequestBody BaseToken baseToken) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<Menu> menuList = menuBusiness.getUserMenu(baseUser);
        return ResponseEntity.buildSuccEntity(menuList);
    }

    @PostMapping("getHomePageMenu")
    public ResponseEntity<List<HomePageMenuVO>> getHomePageMenu(@RequestBody BaseToken baseToken) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<HomePageMenuVO> menuList = menuBusiness.getHomePageMenu(baseUser);
        return ResponseEntity.buildSuccEntity(menuList);
    }
}
