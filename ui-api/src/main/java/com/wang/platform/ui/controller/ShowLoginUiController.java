package com.wang.platform.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wang.platform.beans.ResultInfo;

@RestController
@RequestMapping("ui/login")
public class ShowLoginUiController {

    @GetMapping
    public ResultInfo getAuthInfo() {

    }
}
