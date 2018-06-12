package com.wang.platform.ui.service;

import com.wang.platform.beans.ResultInfo;
import com.wang.platform.beans.TopicMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("msg-c")
public interface MsgService {
    @PostMapping("msg")
    ResultInfo sendMsg(@RequestBody TopicMsg params);
}
