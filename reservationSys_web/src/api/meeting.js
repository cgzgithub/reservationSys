import { request } from './http';

export default request({
    // 会议室列表
    getMeetingrooms: {
        url: '/api/meeting/getMeetingrooms'
    },
    // 新增会议室
    addRoom: {
        url: '/api/meeting/add/meetingRoom'
    },
    // 新增预约
    addBooking: {
        url: '/api/meeting/booking/add'
    },
    // 数据分析
    analysis: {
        url: '/api/meeting/booking/analysis'
    },
     // 取消预约
    cancelBook: {
        url: '/api/meeting/booking/cancel'
    },
    // 编辑预约信息
    editBook: {
        url: '/api/meeting/booking/edit'
    },
    // 未到访
    unVisit: {
        url: '/api/meeting/booking/noVisit'
    },
    // 查询预约列表
    getBookingList: {
        url: '/api/meeting/booking/query'
    },
    // 到访
    visit: {
        url: '/api/meeting/booking/visit'
    },
    // 删除会议室
    deleteRoom: {
        url: '/api/meeting/del/meetingRoom'
    },
    // 编辑会议室
    editRoom: {
        url: '/api/meeting/edit/meetingRoom'
    },
    // 查询会议室
    getAllRoomList: {
        url: '/api/meeting/getAllList'
    },
    // 获取全部路演厅或全部会议室
    getRoomListByType: {
        url: '/api/meeting/getAllMeetingRoomByType'
    },
    // 查询会议室预约时间
    queryMeetingroomTime: {
        url: '/api/meeting/meetingBooking/queryTime'
    },
    // 查询路演厅预约时间
    queryRoadshowTime: {
        url: '/api/meeting/roadshowBooking/queryTime'
    },
    // 开放会议室
    openRoom: {
        url: '/api/meeting/open/meetingroom'
    },
    // 判断预约状态
    timeDecide: {
        url: '/api/meeting/booking/timeDecide'
    },
    // 导出分析数据
    exportAnalysisExcel: {
        url: '/api/meeting/exportAnalysisExcel'
    },
    // 新增会议室附件
    addEnclosure: {
        url: '/api/meeting/add/meetingEnclosure'
    },
    // 删除会议室附件
    delEnclosure: {
        url: '/api/meeting/del/meetingEnclosure'
    },
    // 编辑会议室附件
    editEnclosure: {
        url: '/api/meeting/edit/meetingEnclosure'
    },
    // 查询会议室附件
    getEnclosuresByMeetingId: {
        url: '/api/meeting/getEnclosuresByMeetingId'
    }
});
