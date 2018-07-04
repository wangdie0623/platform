package com.wang.platform.plugins.website;

import com.wang.platform.beans.ResultInfo;
import com.wang.platform.crawler.CookieUtils;
import com.wang.platform.exceptions.ServiceException;
import com.wang.platform.plugins.fegin.RedisFeign;
import com.wang.platform.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class CookieCacheUtils {
    private static final String COOKIE_CACHE_KEY = "webPlugin:cookies";//爬虫cookie存放地址key
    public static RedisFeign feign;

    @Autowired
    public static void setFeign(RedisFeign feign) {
        CookieCacheUtils.feign = feign;
    }

    private static String cookieKey(String token) {
        return RedisKeyUtils.layerKey(COOKIE_CACHE_KEY, token);
    }

    /**
     * 保存cookie信息到redis
     *
     * @param cookieStore
     * @param token
     */
    public static void cacheCookie(CookieStore cookieStore, String token) {
        String json = CookieUtils.toJson(cookieStore);
        feign.pushObjM(cookieKey(token), json, 10l);
    }

    /**
     * 获取redis存在cookie值
     *
     * @param token
     * @return
     */
    public static List<Cookie> getCookies(String token) {
        ResultInfo info = feign.fullVal(cookieKey(token));
        if (info.getDetail() == null || info.getDetail().equals("")) {
            throw new ServiceException(String.format("%s cookie不存在", token));
        }
        String jsonArr = info.getDetail().toString();
        return CookieUtils.jsonToCookie(jsonArr);
    }
}
