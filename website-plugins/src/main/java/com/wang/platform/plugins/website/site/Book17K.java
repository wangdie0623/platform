package com.wang.platform.plugins.website.site;

import com.wang.platform.beans.AuthInfo;
import com.wang.platform.beans.ResultInfo;
import com.wang.platform.crawler.IHttpHelper;
import com.wang.platform.enums.CrawlerCodeEnum;
import com.wang.platform.plugins.annotations.Site;
import com.wang.platform.plugins.website.AbstractWebsitePlugin;
import com.wang.platform.utils.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

@Slf4j
@Site("book17k")
public class Book17K extends AbstractWebsitePlugin {
    private Map<String, String> data;
    private String token;

    public Book17K(IHttpHelper httpHelper) {
        super(httpHelper);
    }

    @Override
    public ResultInfo login(AuthInfo authInfo) {
        try {
            this.data = authInfo.getData();
            String account = data.get("account");
            String password = data.get("password");
            String homeUrl = "http://www.17k.com/";
            httpHelper.doGet(homeUrl);
            String loginUrl = "http://passport.17k.com/login.action?jsonp=true";
            String code = getCode();
            String params = "userName=%s&password=%s&verificationCode=%s&isCode=1&isAutoLogin=true&postCallback=parent.Q.post_artwc_callback";
            httpHelper.addHeader("Referer", homeUrl);
            String result = httpHelper.doPostForm(loginUrl, String.format(params, account, password, code)).respStr();
            String error_code = RegexUtil.getValue("\"error_code\":(.*),", result, 1).trim();
            String error_msg = RegexUtil.getValue("\"error_message\":\"(.*)\"", result, 1).trim();
            log.info("login-result:{},{}", error_code, error_msg);
            if (!StringUtils.isBlank(error_code)) {
                return ResultInfo.create(CrawlerCodeEnum.LOGIN_FAIL, error_msg);
            }
            return ResultInfo.create(CrawlerCodeEnum.LOGIN_SUCCESS);
        } catch (Exception e) {
            log.error("登录异常", e);
        }
        return ResultInfo.create(CrawlerCodeEnum.LOGIN_FAIL, "登录异常");
    }

    @Override
    public ResultInfo collect() {
        return null;
    }

    @Override
    public ResultInfo format() {
        return null;
    }

    @Override
    public ResultInfo saveData() {
        return null;
    }


    private String getCode() {
        String code = null;
        int count = 1;
        while (code == null && count <= 3) {
            String codeUrl = "http://passport.17k.com/mcode.jpg?" + new Date().getTime();
            byte[] respData = httpHelper.doGet(codeUrl).getRespData();
            saveCodeImg(respData);
            Scanner scanner = new Scanner(System.in);
            code = scanner.nextLine();
            count++;
        }
        return code;
    }

    private void saveCodeImg(byte[] data) {
        try (ByteArrayInputStream in = new ByteArrayInputStream(data);
             FileOutputStream out = new FileOutputStream("x.jpg")) {
            byte[] temp = new byte[1024];
            for (int index = in.read(temp); index != -1; index = in.read(temp)) {
                out.write(temp, 0, index);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
