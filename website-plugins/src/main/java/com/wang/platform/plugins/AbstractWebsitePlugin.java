package com.wang.platform.plugins;

import com.wang.platform.beans.AuthInfo;
import com.wang.platform.beans.ResultInfo;
import com.wang.platform.crawler.HttpHelperBuilder;
import com.wang.platform.crawler.IHttpHelper;
import com.wang.platform.plugins.fegin.IRedisService;

public abstract class AbstractWebsitePlugin implements IWebsitePlugin {

    protected IHttpHelper httpHelper = HttpHelperBuilder.builderDefault();

    private IRedisService redisService;

    @Override
    public void execute(AuthInfo authInfo) {
        if (!login(authInfo).isSuccess()) {
            return;
        }
        if (!collect().isSuccess()) {
            return;
        }
        format();
        saveData().isSuccess();
        closeAll();
    }

    @Override
    public void closeAll() {
        httpHelper.closeAll();
    }

    @Override
    public ResultInfo restImgCode() {
        return null;
    }

    @Override
    public ResultInfo restQRCode() {
        return null;
    }

    @Override
    public ResultInfo restPhoneCode() {
        return null;
    }
}
