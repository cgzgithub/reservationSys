import { request } from './http';

export default request({
    // 查询角色列表
    getRoleList: {
        url: '/api/role/getRole'
    },
    // 删除角色
    delRole: {
        url: '/api/role/deleteRole'
    },
    // 编辑角色
    editRole: {
        url: '/api/role/editRole'
    },
    // 查询角色菜单
    getRoleMenu: {
        url: '/api/role/getRoleMenu'
    },
    // 保存角色菜单
    saveRoleMenu: {
        url: '/api/role/saveRoleMenu'
    },
    // 新增角色
    addRole: {
        url: '/api/role/addRole'
    },
});
