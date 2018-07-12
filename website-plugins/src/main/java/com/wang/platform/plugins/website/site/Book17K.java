package com.wang.platform.plugins.website.site;

import com.wang.platform.beans.BaseCrawlerParam;
import com.wang.platform.beans.CrawlerLoginParam;
import com.wang.platform.beans.ResultInfo;
import com.wang.platform.crawler.IHttpHelper;
import com.wang.platform.enums.Base64ImageType;
import com.wang.platform.enums.CrawlerCodeEnum;
import com.wang.platform.plugins.annotations.Site;
import com.wang.platform.plugins.beans.Website;
import com.wang.platform.plugins.website.AbstractWebsitePlugin;
import com.wang.platform.plugins.website.CookieCacheUtils;
import com.wang.platform.utils.Base64ImgUtils;
import com.wang.platform.utils.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Slf4j
@Site("book17k")
public class Book17K extends AbstractWebsitePlugin {
    private Map<String, Object> userInfo;

    public Book17K(IHttpHelper httpHelper) {
        super(httpHelper);
    }


    @Override
    public ResultInfo loginInfo(BaseCrawlerParam param) {
        CookieCacheUtils.cacheCookieSet(param.getToken(), Collections.emptySet());
        homeUrl = "http://www.17k.com/";
        httpHelper.doGet(homeUrl);
        return ResultInfo.create(CrawlerCodeEnum.LOGIN_INFO_SUCCESS, Website.BOOK17K);
    }

    @Override
    public ResultInfo login(CrawlerLoginParam param) {
        try {
            this.userInfo = param.getData();
            String account = MapUtils.getString(userInfo, "account");
            String password = MapUtils.getString(userInfo, "pwd");
            String code = MapUtils.getString(userInfo, "code");
            String loginUrl = "http://passport.17k.com/login.action?jsonp=true";
            String params = "userName=%s&password=%s&verificationCode=%s&isCode=1&isAutoLogin=true&postCallback=parent.Q.post_artwc_callback";
            homeUrl = "http://www.17k.com/";
            httpHelper.addHeader("Referer", homeUrl);
            String result = httpHelper.doPostForm(loginUrl, String.format(params, account, password, code)).respStr();
            String error_code = RegexUtil.getValue("\"error_code\":(.*),", result, 1).trim();
            String error_msg = RegexUtil.getValue("\"error_message\":\"(.*)\"", result, 1).trim();
            log.info("login-result:{},{}", error_code, error_msg);
            if (!StringUtils.isBlank(error_code)) {
                return ResultInfo.create(CrawlerCodeEnum.LOGIN_FAIL, error_msg);
            }
            execute();//执行异步采集流程
            return ResultInfo.create(CrawlerCodeEnum.LOGIN_SUCCESS);
        } catch (Exception e) {
            log.error("登录异常", e);
        }
        return ResultInfo.create(CrawlerCodeEnum.LOGIN_FAIL, "登录异常");
    }

    @Override
    public ResultInfo collect() {
        String userInfoURL = "http://passport.17k.com/get_info_cookie?callback=Q._40_3796_17&jsonp=Q._40_3796_17";
        String str = httpHelper.doGet(userInfoURL).respStr();
        System.out.println(str);
        return ResultInfo.fail("");
    }

    @Override
    public ResultInfo format() {
        return null;
    }

    @Override
    public ResultInfo saveData() {
        return null;
    }

    @Override
    public ResultInfo restImgCode(BaseCrawlerParam param) {
        String codeUrl = "http://passport.17k.com/mcode.jpg?" + new Date().getTime();
        byte[] imgData = httpHelper.doGet(codeUrl).getRespData();
        String imgStr = Base64ImgUtils.getImgStr(imgData, Base64ImageType.JPG);
        return ResultInfo.create(CrawlerCodeEnum.CODE_IMG_SUCCESS, "刷新成功", imgStr);
    }


}
