import { request } from './http';

export default request({
    // 查询所有菜单
    getAllMenu: {
        url: '/api/menu/getMenu'
    },
    // 查询用户菜单
    getUserMenu: {
        url: '/api/menu/getUserMenu'
    },
    // 查询首页导航菜单
    getHomePageMenu: {
        url: '/api/menu/getHomePageMenu'
    }
});
