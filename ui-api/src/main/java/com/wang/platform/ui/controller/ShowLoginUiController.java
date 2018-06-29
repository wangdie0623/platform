package com.wang.platform.ui.controller;

import com.wang.platform.beans.TopicMsg;
import com.wang.platform.ui.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wang.platform.beans.ResultInfo;

@RestController
@RequestMapping("ui/login")
public class ShowLoginUiController {

    @Autowired
    private MsgService msgService;

    @GetMapping
    public ResultInfo getAuthInfo() {
        TopicMsg msg = new TopicMsg();
        msg.setTopic("json-ApplyLoginInfo");
        msg.setData("17k");
        return msgService.sendMsg(msg);
    }

    @PostMapping
    public ResultInfo login() {
        return ResultInfo.fail("登录失败");
    }
}
