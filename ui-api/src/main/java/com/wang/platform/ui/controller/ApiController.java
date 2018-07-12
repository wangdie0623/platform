package com.wang.platform.ui.controller;

import com.wang.platform.beans.BaseCrawlerParam;
import com.wang.platform.beans.CrawlerLoginParam;
import com.wang.platform.beans.CrawlerRestParam;
import com.wang.platform.beans.ResultInfo;
import com.wang.platform.enums.CrawlerRestTypeEnum;
import com.wang.platform.ui.service.WebsitePluginFeign;
import com.wang.platform.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api")
public class ApiController {
    @Autowired
    private WebsitePluginFeign pluginFeign;

    @GetMapping("info")
    public ResultInfo info(String name) {
        String token = UUIDUtil.generate();
        BaseCrawlerParam param = new BaseCrawlerParam(token, name);
        MDC.put("token", token);
        ResultInfo info = pluginFeign.info(param);
        if (info.isSuccess()) {
            info.setMsg(String.format("查询%s,登录参数成功", name));
            info.setToken(token);
        }
        return info;
    }

    @PostMapping("login")
    public ResultInfo login(@RequestBody Map<String, Object> params) {
        String token = MapUtils.getString(params, "token");
        String name = MapUtils.getString(params, "siteName");
        MDC.put("token", token);
        CrawlerLoginParam loginParam = new CrawlerLoginParam(token, name);
        loginParam.getData().putAll(params);
        ResultInfo info = pluginFeign.login(loginParam);
        info.setToken(token);
        return info;
    }

    @GetMapping("restImg")
    public ResultInfo restImg(@RequestParam String token,
                              @RequestParam String siteName) {
        MDC.put("token", token);
        CrawlerRestParam restParam = new CrawlerRestParam(token, siteName);
        restParam.setRestType(CrawlerRestTypeEnum.IMG_CODE);
        ResultInfo info = pluginFeign.rest(restParam);
        info.setToken(token);
        return info;
    }

    @GetMapping("restQImg")
    public ResultInfo restQImg(@RequestParam String token,
                               @RequestParam String siteName) {
        MDC.put("token", token);
        CrawlerRestParam restParam = new CrawlerRestParam(token, siteName);
        restParam.setRestType(CrawlerRestTypeEnum.QIMG_CODE);
        ResultInfo info = pluginFeign.rest(restParam);
        info.setToken(token);
        return info;
    }

    @GetMapping("restPhoneCode")
    public ResultInfo restPhoneCode(@RequestParam String token,
                                    @RequestParam String siteName) {
        MDC.put("token", token);
        CrawlerRestParam restParam = new CrawlerRestParam(token, siteName);
        restParam.setRestType(CrawlerRestTypeEnum.PHONE_CODE);
        ResultInfo info = pluginFeign.rest(restParam);
        info.setToken(token);
        return info;
    }
}
