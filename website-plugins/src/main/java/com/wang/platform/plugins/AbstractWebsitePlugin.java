package com.wang.platform.plugins;

import com.wang.platform.beans.AuthInfo;
import com.wang.platform.crawler.HttpHelperBuilder;
import com.wang.platform.crawler.IHttpHelper;

public abstract class AbstractWebsitePlugin implements IWebsitePlugin {

    protected IHttpHelper httpHelper = HttpHelperBuilder.builderDefault();

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
}
