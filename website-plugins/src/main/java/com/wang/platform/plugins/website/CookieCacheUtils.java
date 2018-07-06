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
    private static RedisFeign feign;

    @Autowired
    public static void setFeign(RedisFeign feign) {
        CookieCacheUtils.feign = feign;
    }

    private static String cookieKey(String token) {
        return RedisKeyUtils.layerKey(COOKIE_CACHE_KEY, token);
    }

    /**
     * 保存cookieStore格式cookie信息到redis
     *
     * @param cookieStore
     * @param token
     */
    public static void cacheCookieStore(CookieStore cookieStore, String token) {
        String json = CookieUtils.toJson(cookieStore);
        cacheCookieStr(token, json);
    }

    /**
     * 保存字符串形式cookie
     *
     * @param token
     * @param cookies
     */
    public static void cacheCookieStr(String token, String cookies) {
        feign.pushObjM(cookieKey(token), cookies, 10l);
    }

    /**
     * 获取redis存在cookieStore格式值
     *
     * @param token
     * @return
     */
    public static List<Cookie> getCookieStore(String token) {
        ResultInfo info = feign.fullVal(cookieKey(token));
        if (info.getDetail() == null || info.getDetail().equals("")) {
            throw new ServiceException(String.format("%s cookie不存在", token));
        }
        String jsonArr = info.getDetail().toString();
        return CookieUtils.jsonToCookie(jsonArr);
    }

    /**
     * 获取字符串格式cookie值
     * @param token
     * @return
     */
    public static String getCookieStr(String token) {
        ResultInfo info = feign.fullVal(cookieKey(token));
        if (info.getDetail() == null || info.getDetail().equals("")) {
            throw new ServiceException(String.format("%s cookie不存在", token));
        }
        return info.toString();
    }
}
