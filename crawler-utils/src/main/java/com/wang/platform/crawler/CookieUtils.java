package com.wang.platform.crawler;

import com.alibaba.fastjson.JSON;
import com.wang.platform.utils.BeanCopyUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.NetscapeDraftHeaderParser;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CookieUtils {

    /**
     * 得到json格式cookie集合
     *
     * @param cookies
     * @return
     */
    public static String toJson(Set<Cookie> cookies) {
        return JSON.toJSONString(cookies);
    }

    /**
     * 转换json字符串为cookie集合
     *
     * @param arrStr
     * @return
     */
    public static Set<Cookie> jsonToCookie(String arrStr) {
        Set<Map<String, String>> jsonArr = JSON.parseObject(arrStr, Set.class);
        Set<Cookie> set = new HashSet<>();
        jsonArr.forEach(it -> {
            CustomCookie cookie = new CustomCookie(MapUtils.getString(it, "name"),
                    MapUtils.getString(it, "value"));
            BeanCopyUtils.copyProperties(cookie, it);
            set.add(cookie);
        });
        return set;
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
     * 将旧cookie跟新响应cookie合并
     *
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

    /**
     * cookie集合转字符串
     *
     * @param cookies
     * @return
     */
    public static String setToStr(Set<Cookie> cookies) {
        if (cookies == null || cookies.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        cookies.forEach(it -> {
            builder.append(it.getName() + "=" + it.getValue() + ";");
        });
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static void main(String[] args) {
        IHttpHelper helper = HttpHelperBuilder.builderDefault();
        Header[] headers = helper.doGet("https://passport.csdn.net/account/login?from=https://mp.csdn.net/")
                .getRespHeaders();
        BasicHeader header = new BasicHeader("set-cookie",
                "Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1530844983,1530847613,1530849612,1530867361; JSESSIONID=35892B8971685AB156E32E18DEAF1BF5.tomcat1; LSSC=LSSC-1401510-vWtcFWNlqIgGoQj1JdCb9SgR4UU2wd-passport.csdn.net; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1530867363; dc_tos=pbfste");
        headers[0]=header;
        Set<Cookie> cookies = newCookies(null, headers);
        System.out.println(setToStr(cookies));
    }
}
