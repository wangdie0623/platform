package com.wang.platform.ui.service;

import com.wang.platform.beans.BaseCrawlerParam;
import com.wang.platform.beans.CrawlerLoginParam;
import com.wang.platform.beans.CrawlerRestParam;
import com.wang.platform.beans.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient("plugins")
public interface WebsitePluginFeign {

    @PostMapping("loginInfo")
    public ResultInfo info(@RequestBody BaseCrawlerParam param);

    @PostMapping("login")
    public ResultInfo login(@RequestBody CrawlerLoginParam param);

    @PostMapping("rest")
    public ResultInfo rest(@RequestBody CrawlerRestParam param);
}
