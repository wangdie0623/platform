package com.wang.platform.plugins.website;

import com.wang.platform.beans.AuthInfo;
import com.wang.platform.beans.ResultInfo;
import com.wang.platform.crawler.IHttpHelper;
import com.wang.platform.enums.CrawlerCodeEnum;

public abstract class AbstractWebsitePlugin implements IWebsitePlugin {

    protected IHttpHelper httpHelper;

    protected String homeUrl;//主页地址
    protected String codeUrl;//图片验证码刷新地址
    protected String qCodeUrl;//二维码图片刷新地址
    protected String phoneCodeUrl;//短信验证刷新地址

    protected AbstractWebsitePlugin(IHttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

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
        return ResultInfo.create(CrawlerCodeEnum.CONTROL_ERROR, "暂未实现");
    }

    @Override
    public ResultInfo restQRCode() {
        return ResultInfo.create(CrawlerCodeEnum.CONTROL_ERROR, "暂未实现");
    }

    @Override
    public ResultInfo restPhoneCode() {
        return ResultInfo.create(CrawlerCodeEnum.CONTROL_ERROR, "暂未实现");
    }


}
