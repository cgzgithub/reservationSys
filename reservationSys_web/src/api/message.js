import { request } from './http';

export default request({
    // 获取首页消息
    getMessageList: {
        url: '/api/message/getMessageList'
    }
});
