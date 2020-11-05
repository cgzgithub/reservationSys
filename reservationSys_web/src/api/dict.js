import { request } from './http';

export default request({
    // 通过字典type获取字典对应数据
    getDictInfoByType: {
        url: '/api/dict/getDictInfoByType'
    },
    // 新增字典数据
    addDictInfo: {
        url: '/api/dict/addDictInfo'
    },
    // 新增字典
    addDictType: {
        url: '/api/dict/addDictType'
    },
    // 删除字典数据
    deleteDictInfo: {
        url: '/api/dict/deleteDictInfo'
    },
    // 删除字典
    deleteDictType: {
        url: '/api/dict/deleteDictType'
    },
    // 编辑字典数据
    editDictInfo: {
        url: '/api/dict/editDictInfo'
    },
    // 编辑字典
    editDictType: {
        url: '/api/dict/editDictType'
    },
    // 查询全部字典
    getAllDictTypeList: {
        url: '/api/dict/getDictTypeList'
    },
    // 按条件查询字典数据
    getDictInfoByCondition: {
        url: '/api/dict/getDictInfoByCondition'
    }
});
