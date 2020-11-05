import { request } from './http';

export default request({
    // 登录
    login: {
        url: '/api/user/login'
    },
    // 退出登录
    logout: {
        url: '/api/user/logout'
    },
    // 修改密码
    changePwd: {
        url: '/api/user/changePwd'
    },
    // 跳转登录
    pcLogin: {
        url: '/api/user/pcLogin'
    }
});
