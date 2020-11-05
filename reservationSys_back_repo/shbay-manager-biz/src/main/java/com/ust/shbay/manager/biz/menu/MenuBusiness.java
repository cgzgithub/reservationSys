package com.ust.shbay.manager.biz.menu;

import com.ust.shbay.service.base.BaseUser;
import com.ust.shbay.manager.entity.vo.HomePageMenuVO;
import com.ust.shbay.service.menu.vo.Menu;

import java.util.List;

/**
 *
 */
public interface MenuBusiness {

    /**
     * 根据系统查询所有菜单
     *
     * @param baseUser
     * @return
     */
    List<Menu> getAllMenu(BaseUser baseUser);

    /**
     * 获取用户可见菜单
     *
     * @param baseUser
     * @return
     */
    List<Menu> getUserMenu(BaseUser baseUser);

    /**
     * 首页功能菜单显示
     * @param baseUser
     * @return
     */
    List<HomePageMenuVO> getHomePageMenu(BaseUser baseUser);
}
