package com.ust.shbay.service.menu;

import com.ust.shbay.manager.dao.SyMenuMapper;
import com.ust.shbay.manager.entity.*;
import com.ust.shbay.service.base.BaseUser;
import com.ust.shbay.manager.entity.vo.HomePageMenuVO;
import com.ust.shbay.service.menu.vo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private SyMenuMapper syMenuMapper;

    @Override
    public List<Menu> getAllMenu(BaseUser baseUser) {
        //获取全部菜单
        List<SyMenu> symenuList = syMenuMapper.selectAll();
        //获取菜单信息列表
        List<Menu> menuList = getMenuTree(symenuList);
        return menuList;
    }

    @Override
    public List<Menu> getUserMenu(BaseUser baseUser) {
        //获取当前用户拥有权限的菜单id列表
        List<SyMenu> symenuList = syMenuMapper.selectAccountMenu(baseUser.getSyAccountId());
        //获取菜单信息列表
        List<Menu> menuList = getMenuTree(symenuList);
        return menuList;
    }

    /**
     * 首页功能菜单显示
     * @param baseUser
     * @return
     */
    @Override
    public List<HomePageMenuVO> getHomePageMenu(BaseUser baseUser){
        List<HomePageMenuVO> list = new ArrayList<>();
        List<SyMenu> menuList = syMenuMapper.selectAccountMenu(baseUser.getSyAccountId());
        if(menuList.size() > 0){
            list = getHomePageMenu(menuList);
        }
        return list;
    }

    private List<Menu> getMenuTree(List<SyMenu> syMenuList) {
        List<Menu> menuTree = new ArrayList<>();

        //构建第一级菜单
        for (SyMenu syMenu : syMenuList) {
            if (syMenu.getParentId() != 0)
                continue;
            Menu menu = new Menu();
            menu.setId(syMenu.getId());
            menu.setText(syMenu.getName());
            menu.setPath(syMenu.getPath());
            menu.setFullPath(syMenu.getPath());
            menuTree.add(menu);
        }

        //构建第二级菜单
        for (Menu flMenu : menuTree) {
            for (SyMenu syMenu : syMenuList) {
                if (syMenu.getParentId() == 0 || !flMenu.getId().equals(syMenu.getParentId())) {
                    continue;
                }
                Menu menu = new Menu();
                menu.setId(syMenu.getId());
                menu.setText(syMenu.getName());
                menu.setPath(syMenu.getPath());
                menu.setFullPath(flMenu.getPath() + "/" + syMenu.getPath());

                if (flMenu.getSubMenu() == null)
                    flMenu.setSubMenu(new ArrayList<Menu>());
                flMenu.getSubMenu().add(menu);
            }
        }
        return menuTree;
    }

    //获取首页功能菜单
    private List<HomePageMenuVO> getHomePageMenu(List<SyMenu> syMenuList) {
        List<HomePageMenuVO> list = new ArrayList<>();

        //构建第一级菜单
        for (SyMenu syMenu : syMenuList) {
            if(syMenu.getName().equals("首页")){
                continue;
            }
            if (syMenu.getParentId() != 0){
                continue;
            }
            HomePageMenuVO menu = new HomePageMenuVO();
            menu.setName(syMenu.getName());
            menu.setIcon(syMenu.getIcon());
            for (SyMenu subMenu : syMenuList) {
                if (subMenu.getParentId() == 0 || !syMenu.getId().equals(subMenu.getParentId())) {
                    continue;
                }
                menu.setSubMenuFullPath(syMenu.getPath() + "/" + subMenu.getPath());
                break;
            }
            list.add(menu);
        }
        return list;
    }
}
