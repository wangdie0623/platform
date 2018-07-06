package com.wang.platform.crawler;

import com.alibaba.fastjson.JSON;
import com.wang.platform.utils.BeanCopyUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.NetscapeDraftHeaderParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

import java.util.*;

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

    /**
     * 处理单个请求响应头
     * 不是Set-cookie返回null
     * 是set-cookie过滤处理
     *
     * @param respHeader
     * @return
     */
    private static Set<Cookie> getRespHeaderCookie(Header respHeader) {
        if (!respHeader.getName().equalsIgnoreCase("set-Cookie")) {
            return null;
        }
        final NetscapeDraftHeaderParser parser = NetscapeDraftHeaderParser.DEFAULT;
        final String s = respHeader.getValue();
        final CharArrayBuffer buffer = new CharArrayBuffer(s.length());
        buffer.append(s);
        final ParserCursor cursor = new ParserCursor(0, buffer.length());
        HeaderElement[] elements = new HeaderElement[]{parser.parseHeader(buffer, cursor)};
        if (elements == null || elements.length == 0) {
            return null;
        }

        Set<Cookie> cookies = new HashSet<>();
        for (HeaderElement item : elements) {
            cookies.add(new CustomCookie(item.getName(), item.getValue()));
        }
        if (cookies.size() == 0) {
            return null;
        }
        return cookies;
    }

    /**
     * @param oldCookies
     * @param respHeaders
     * @return
     */
    public static Set<Cookie> newCookies(Set<Cookie> oldCookies, Header[] respHeaders) {
        Set<Cookie> newCookies = new HashSet<>();
        if (oldCookies != null) {
            newCookies.addAll(oldCookies);
        }
        if (respHeaders == null || respHeaders.length == 0) {
            return newCookies;
        }
        for (Header item : respHeaders) {
            Set<Cookie> itemCookie = getRespHeaderCookie(item);
            if (itemCookie != null) {
                newCookies.removeAll(itemCookie);
                newCookies.addAll(itemCookie);
            }
        }
        return newCookies;
    }

    public static void main(String[] args) {
        CustomCookie one = new CustomCookie("xx", "");
        CustomCookie two= new CustomCookie("xx", "1");
        Set<Cookie> result = new HashSet<>();
        result.add(one);
        result.remove(one);
        result.add(two);
        System.out.println(result);
    }
}
