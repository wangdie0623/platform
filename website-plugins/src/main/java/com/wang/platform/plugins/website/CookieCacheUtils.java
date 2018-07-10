package com.wang.platform.plugins.website;

import com.wang.platform.beans.ResultInfo;
import com.wang.platform.crawler.CookieUtils;
import com.wang.platform.plugins.fegin.RedisFeign;
import com.wang.platform.utils.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.cookie.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;


@Slf4j
@Component
public class CookieCacheUtils {
    private static final String COOKIE_CACHE_KEY = "webPlugin:cookies";//爬虫cookie存放地址key
    private static RedisFeign feign;

    @Autowired
    public void setFeign(RedisFeign feign) {
        CookieCacheUtils.feign = feign;
    }

    private static String cookieKey(String token) {
        return RedisKeyUtils.layerKey(COOKIE_CACHE_KEY, token);
    }

    /**
     * 保存cookie集合到redis
     *
     * @param cookies
     * @param token
     */
    public static void cacheCookieSet(String token, Set<Cookie> cookies) {
        String json = CookieUtils.toJson(cookies);
        cacheCookieStr(token, json);
    }

    /**
     * 保存字符串cookie到redis
     *
     * @param token
     * @param cookies
     */
    public static void cacheCookieStr(String token, String cookies) {
        feign.pushObjM(cookieKey(token), cookies, 10l);
    }

    /**
     * 根据token获取cookie集合
     *
     * @param token
     * @return
     */
    public static Set<Cookie> getCookieSet(String token) {
        ResultInfo info = feign.fullVal(cookieKey(token));
        if (info.getDetail() == null || info.getDetail().equals("")) {
            throw new TokenInvalidException(String.format("%s cookie不存在", token));
        }
        String jsonArr = info.getDetail().toString();
        return CookieUtils.jsonToCookie(jsonArr);
    }


}
