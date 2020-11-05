import { request } from './http';

export default request({
    // 新增合同
    addContract: {
        url: '/api/contract/add/contract'
    },
    // 新增合同付款节点
    addContractNode: {
        url: '/api/contract/add/contractNode'
    },
    // 删除合同
    deleteContract: {
        url: '/api/contract/del/Contract'
    },
    // 删除合同付款节点
    deleteContractNode: {
        url: '/api/contract/del/ContractNode'
    },
    // 编辑合同
    editContract: {
        url: '/api/contract/edit/contract'
    },
    // 编辑合同付款节点
    editContractNode: {
        url: '/api/contract/edit/contractNode'
    },
    // 查询合同列表
    getContractList: {
        url: '/api/contract/getContractList'
    },
    // 通过合同id查询合同付款节点列表
    getNodeListById: {
        url: '/api/contract/getNodeListById'
    },
    // 导出合同数据
    exportContractExcel: {
        url: '/api/contract/exportContractExcel'
    }
});
