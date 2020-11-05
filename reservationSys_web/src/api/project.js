import { request } from './http';

export default request({
    // 查询项目列表
    getAllList: {
        url: '/api/project/getAllList'
    },
    // 新增项目
    addProject: {
        url: '/api/project/add/project'
    },
    // 关闭项目
    deleteProject: {
        url: '/api/project/del/project'
    },
    // 开放项目
    openProject: {
        url: '/api/project/open/project'
    },
    // 编辑项目基本信息
    editProject: {
        url: '/api/project/edit/project'
    },
    // 查询项目流程及节点
    getStageNodeList: {
        url: '/api/project/getStageNodeList'
    },
    // 查询项目阶段节点字典
    getStageNodeDictList: {
        url: '/api/project/getStageNodeDictList'
    },
    // 查询项目节点字典
    getNodeDictList: {
        url: '/api/project/getNodeDictList'
    },
    // 查询项目阶段字典
    getStageDictList: {
        url: '/api/project/getStageDictList'
    },
    // 查询当前阶段最后一个可自定义节点
    getLastCustomNode: {
        url: '/api/project/getLastCustomNode'
    },
    // 添加自定义节点记录信息
    addStageNodeInfo: {
        url: '/api/project/addStageNodeInfo'
    },
    // 删除自定义节点记录信息
    delStageNodeInfo: {
        url: '/api/project/delStageNodeInfo'
    },
    // 编辑自定义节点记录信息
    editStageNodeInfo: {
        url: '/api/project/editStageNodeInfo'
    },
    // 查询资料
    getArchivingList: {
        url: '/api/project/search/archiving'
    },
    // 新增资料
    addArchiving: {
        url: '/api/project/add/archiving'
    },
    // 删除资料
    delArchiving: {
        url: '/api/project/del/archiving'
    },
    // 编辑资料
    editArchiving: {
        url: '/api/project/edit/archiving'
    },
    // 导出项目数据
    exportProjectExcel: {
        url: '/api/project/exportProjectExcel'
    }
});
