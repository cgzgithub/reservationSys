import { request } from './http';

export default request({
    // 查询公司列表
    getCompanyList: {
        url: '/api/visitCompany/getAllList'
    }
});
