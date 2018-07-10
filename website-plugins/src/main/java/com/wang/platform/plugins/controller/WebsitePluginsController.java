package com.wang.platform.plugins.controller;

import com.wang.platform.beans.BaseCrawlerParam;
import com.wang.platform.beans.CrawlerLoginParam;
import com.wang.platform.beans.CrawlerRestParam;
import com.wang.platform.beans.ResultInfo;
import com.wang.platform.enums.CrawlerCodeEnum;
import com.wang.platform.enums.CrawlerRestTypeEnum;
import com.wang.platform.plugins.utils.WebsiteFactory;
import com.wang.platform.plugins.website.IWebsitePlugin;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class WebsitePluginsController {


    @PostMapping("loginInfo")
    public ResultInfo info(@RequestBody BaseCrawlerParam param) {
        MDC.put("token", param.getToken());
        IWebsitePlugin plugin = WebsiteFactory.getSite(param.getSiteName());
        if (plugin == null) {
            return ResultInfo.create(CrawlerCodeEnum.LOGIN_INFO_FAIL, "暂不支持此网站");
        }
        return plugin.loginInfo(param);
    }

    @PostMapping("login")
    public ResultInfo login(@RequestBody CrawlerLoginParam param) {
        MDC.put("token", param.getToken());
        IWebsitePlugin plugin = WebsiteFactory.getSite(param.getSiteName());
        return plugin.login(param);
    }

    @PostMapping("rest")
    public ResultInfo rest(@RequestBody CrawlerRestParam param) {
        MDC.put("token", param.getToken());
        IWebsitePlugin plugin = WebsiteFactory.getSite(param.getSiteName());
        CrawlerRestTypeEnum restType = param.getRestType();
        switch (restType) {
            case IMG_CODE:
                return plugin.restImgCode(param);
            case QIMG_CODE:
                return plugin.restQRCode(param);
            case PHONE_CODE:
                return plugin.restPhoneCode(param);
            default:
                log.warn("发现不支持刷新类型");
                return ResultInfo.create(CrawlerCodeEnum.OTHER_FAIL, "刷新失败");
        }
    }
}
