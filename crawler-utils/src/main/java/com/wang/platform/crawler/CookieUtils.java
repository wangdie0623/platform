package com.wang.platform.crawler;

import com.alibaba.fastjson.JSON;
import com.wang.platform.utils.BeanCopyUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CookieUtils {

    public static String toJson(CookieStore cookieStore) {
        List<Cookie> cookies = cookieStore.getCookies();
        return JSON.toJSONString(cookies);
    }

    public static List<Cookie> jsonToCookie(String arrStr) {
        List<Map<String, String>> jsonArr = JSON.parseObject(arrStr, List.class);
        List<Cookie> list = new ArrayList<>();
        jsonArr.forEach(it -> {
            BasicClientCookie cookie = new BasicClientCookie(MapUtils.getString(it, "name"),
                    MapUtils.getString(it, "value"));
            BeanCopyUtils.copyProperties(cookie, it);
            list.add(cookie);
        });
        return list;
    }


}
