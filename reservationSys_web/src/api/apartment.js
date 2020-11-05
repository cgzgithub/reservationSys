import { request } from './http';

export default request({
    // 查询公寓列表
    getApartmentByCondition: {
        url: '/api/apartment/getApartmentByCondition'
    },
    // 新增公寓
    addApartment: {
        url: '/api/apartment/add/apartment'
    },
    // 编辑公寓
    editApartment: {
        url: '/api/apartment/edit/apartment'
    },
    // 删除公寓
    delApartment: {
        url: '/api/apartment/del/apartment'
    },
     // 查询公司列表
     getCompany: {
        url: '/api/apartment/company/query'
    },
    // 添加单位
    addCompany: {
        url: '/api/apartment/add/company'
    },
    // 编辑单位
    editCompany: {
        url: '/api/apartment/edit/company'
    },
    // 删除单位
    delCompany: {
        url: '/api/apartment/del/company'
    },
    //查询申请列表
    queryApplyList: {
        url: '/api/apartment/queryApplyList'
    },
    //查询审核历史列表
    queryReviewList: {
        url: '/api/apartment/queryReviewList'
    },
    //新增公寓申请
    addApartmentApply: {
        url: '/api/apartment/add/Apartmentapply'
    },
    //提审
    arraignment: {
        url: '/api/apartment/arraignment/Apartmentapply'
    },
    //克隆申请不通过
    cloneApply: {
        url: '/api/apartment/clone/Apartmentapply'
    },
    //编辑公寓申请列表
    editApartmentApply: {
        url: '/api/apartment/edit/Apartmentapplylist'
    },
    //提审是否通过状态
    passApartmentReview: {
        url: '/api/apartment/pass/ApartmentReview'
    },
    //查看公寓申请细节
    viewApartmentApply: {
        url: '/api/apartment/see/Apartmentapply'
    },
    // 保存合同数据
    saveContract: {
        url: '/api/apartment/saveContract'
    },
    // 导出申请数据
    exportApplyExcel: {
        url: '/api/apartment/exportApplyExcel'
    }
});
