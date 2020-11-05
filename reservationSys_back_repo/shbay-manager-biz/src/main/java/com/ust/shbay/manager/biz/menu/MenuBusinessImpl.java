package com.ust.shbay.manager.biz.menu;

import com.ust.shbay.service.menu.MenuService;
import com.ust.shbay.manager.entity.vo.HomePageMenuVO;
import com.ust.shbay.service.menu.vo.Menu;
import com.ust.shbay.service.base.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  0017.
 */
@Service
public class MenuBusinessImpl implements MenuBusiness {

    @Autowired
    public MenuService menuService;

    @Override
    public List<Menu> getAllMenu(BaseUser baseUser) {
        //获取菜单信息列表
        List<Menu> menuList = menuService.getAllMenu(baseUser);
        return menuList;
    }

    @Override
    public List<Menu> getUserMenu(BaseUser baseUser) {

        List<Menu> menuList = menuService.getUserMenu(baseUser);
        return menuList;
    }

    /**
     * 首页功能菜单显示
     * @param baseUser
     * @return
     */
    @Override
    public List<HomePageMenuVO> getHomePageMenu(BaseUser baseUser){

        List<HomePageMenuVO> list = menuService.getHomePageMenu(baseUser);
        return list;
    }
}
