package com.ust.shbay.manager.biz.message;

import com.ust.shbay.manager.biz.message.vo.MessageVO;
import com.ust.shbay.service.base.BaseUser;

import java.util.List;

public interface MessageBusiness {

    /**
     * 获取人才公寓审批消息和会议室、展厅、路演厅预约消息
     * @param baseUser
     * @return
     */
    List<MessageVO> getMessageList(BaseUser baseUser);
}
