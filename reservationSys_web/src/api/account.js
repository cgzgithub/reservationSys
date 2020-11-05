import { request } from './http';

export default request({
    // 查询账号列表
    getAccountList: {
        url: '/api/account/getAccount'
    },
    // 编辑账号
    editAccount: {
        url: '/api/account/editAccount'
    },
    // 删除账号
    delAccount: {
        url: '/api/account/delAccount'
    },
    // 开通账号
    openAccount: {
        url: '/api/account/openAccount'
    },
    // 获取用户信息
    getAccountDetail: {
        url: '/api/account/getAccountDetail'
    }
});
