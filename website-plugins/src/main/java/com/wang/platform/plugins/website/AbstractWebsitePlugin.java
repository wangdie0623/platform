package com.wang.platform.plugins.website;

import com.wang.platform.beans.BaseCrawlerParam;
import com.wang.platform.beans.ResultInfo;
import com.wang.platform.crawler.IHttpHelper;
import com.wang.platform.enums.CrawlerCodeEnum;
import org.slf4j.MDC;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractWebsitePlugin implements IWebsitePlugin {

    private static final ExecutorService COLLECT_POOL = Executors.newFixedThreadPool(10);
    protected IHttpHelper httpHelper;

    protected String homeUrl;//主页地址
    protected String codeUrl;//图片验证码刷新地址
    protected String qCodeUrl;//二维码图片刷新地址
    protected String phoneCodeUrl;//短信验证刷新地址

    protected AbstractWebsitePlugin(IHttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }


    //登录成功异步执行采集
    public void execute() {
        String token = MDC.get("token");
        COLLECT_POOL.execute(() -> {
            MDC.put("token", token);
            if (!collect().isSuccess()) {
                return;
            }
            format();
            saveData();
        });
    }

    @Override
    public void closeAll() {
        httpHelper.closeAll();
    }

    @Override
    public ResultInfo restImgCode(BaseCrawlerParam param) {
        return ResultInfo.create(CrawlerCodeEnum.CONTROL_ERROR, "暂未实现");
    }

    @Override
    public ResultInfo restQRCode(BaseCrawlerParam param) {
        return ResultInfo.create(CrawlerCodeEnum.CONTROL_ERROR, "暂未实现");
    }

    @Override
    public ResultInfo restPhoneCode(BaseCrawlerParam param) {
        return ResultInfo.create(CrawlerCodeEnum.CONTROL_ERROR, "暂未实现");
    }

    @Override
    public ResultInfo collect() {
        return ResultInfo.fail("");
    }
}
