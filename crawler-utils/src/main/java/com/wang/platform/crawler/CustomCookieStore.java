package com.wang.platform.crawler;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 替换httpClient默认实现的cookieStore
 */
public class CustomCookieStore implements CookieStore, Serializable {

    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public List<Cookie> getCookies() {
        return new ArrayList<>();
    }

    @Override
    public boolean clearExpired(Date date) {
        return false;
    }

    @Override
    public void clear() {

    }
}
