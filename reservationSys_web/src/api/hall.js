import { request } from './http';

export default request({
    // 展厅预约数据查询
    getHallResevationDetail: {
        url: '/api/hall/getHallResevationDetail'
    },
    // 获取讲解员列表
    getGuideList: {
        url: '/api/showroom/guide/getAllList'
    },
    // 新增预约
    addReservation: {
        url: '/api/showroom/booking/add'
    },
    // 数据分析
    analysis: {
        url: '/api/showroom/booking/analysis'
    },
    // 取消预约
    cancelReservation: {
        url: '/api/showroom/booking/cancel'
    },
    // 编辑预约信息
    editReservation: {
        url: '/api/showroom/booking/edit'
    },
    // 未到访
    unVisit: {
        url: '/api/showroom/booking/noVisit'
    },
    // 查询预约列表
    getReservationList: {
        url: '/api/showroom/booking/query'
    },
    // 查询展厅预约时间
    getReservationTime: {
        url: '/api/showroom/booking/queryTime'
    },
    // 到访
    visit: {
        url: '/api/showroom/booking/visit'
    },
    // 新增讲解员
    addGuide: {
        url: '/api/showroom/guide/add'
    },
    // 删除讲解员
    deleteGuide: {
        url: '/api/showroom/guide/delete'
    },
    // 编辑讲解员
    editGuide: {
        url: '/api/showroom/guide/edit'
    },
    // 判断预约状态
    timeDecide: {
        url: '/api/meeting/booking/timeDecide'
    },
    // 查询展厅
    getRoomList: {
        url: '/api/showroom/getAllShowroomList'
    },
    // 编辑展厅
    editRoom: {
        url: '/api/showroom/edit/showroom'
    },
    // 删除展厅
    delRoom: {
        url: '/api/showroom/del/showroom'
    },
    // 新增展厅
    addRoom: {
        url: '/api/showroom/add/showroom'
    },
    // 开放展厅
    openRoom: {
        url: '/api/showroom/open/showroom'
    },
    // 数据分析
    analysis: {
        url: '/api/showroom/booking/analysis'
    },
    // 导出分析数据
    exportAnalysisExcel: {
        url: '/api/showroom/exportAnalysisExcel'
    },
    // 获取默认讲解员id
    getDefaultGuide: {
        url: '/api/showroom/getDefaultGuide'
    }
});
