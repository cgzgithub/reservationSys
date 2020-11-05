package com.ust.shbay.manager.api.controller;

import com.ust.shbay.common.dto.ResponseEntity;
import com.ust.shbay.manager.biz.message.MessageBusiness;
import com.ust.shbay.manager.biz.message.vo.MessageVO;
import com.ust.shbay.service.base.BaseController;
import com.ust.shbay.service.base.BaseToken;
import com.ust.shbay.service.base.BaseUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "消息模块")
@RestController
@RequestMapping("api/message/")
public class MessageController extends BaseController {

    @Autowired
    private MessageBusiness messageBusiness;

    @ApiOperation(value = "获取首页消息")
    @PostMapping("/getMessageList")
        public ResponseEntity getMessageList(@RequestBody BaseToken baseToken) {
        BaseUser baseUser = new BaseUser();
        setBaseAccount(baseUser);
        List<MessageVO> list = messageBusiness.getMessageList(baseUser);
        return ResponseEntity.buildSuccEntity(list);
    }

}
