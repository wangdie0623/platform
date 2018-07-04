package com.wang.platform.crawler;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;

import java.net.CookieStore;
import java.util.concurrent.ConcurrentHashMap;

public class HttpHelperBuilder {

    private ConcurrentHashMap<String, String> defaultHeaders;
    private CustomCookieStore cookieStore;
    private RequestConfig config;

    private HttpHelperBuilder() {
    }

    public IHttpHelper builder() {
        SimpleHttpHelper result = null;
        if (cookieStore != null) {
            result = new SimpleHttpHelper(new BasicCookieStore());
        } else {
            result = new SimpleHttpHelper(new BasicCookieStore());
        }
        if (defaultHeaders != null) {
            result.setDefaultHeaders(defaultHeaders);
        }
        result.initDefault();
        if (config != null) {
            result.setGlobalConfig(config);
            result.setConfig(config);
        }
        return result;
    }

    public static IHttpHelper builderDefault() {
        return new SimpleHttpHelper(new BasicCookieStore());
    }

    public static HttpHelperBuilder custom() {
        return new HttpHelperBuilder();
    }

    public HttpHelperBuilder setCookie(CustomCookieStore cookie) {
        this.cookieStore = cookie;
        return this;
    }

    public HttpHelperBuilder setDefaultHeaders(ConcurrentHashMap<String, String> headers) {
        this.defaultHeaders = headers;
        return this;
    }

    public HttpHelperBuilder setConfig(RequestConfig requestConfig) {
        this.config = requestConfig;
        return this;
    }

    public HttpHelperBuilder setConfig(String host, Integer port) {
        this.config = RequestConfig
                .copy(HttpPoolFactory.DEFAULT_CONF)
                .setProxy(new HttpHost(host, port, "http"))
                .build();
        return this;
    }

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("xx", "fff");
        IHttpHelper helper = HttpHelperBuilder
                .custom()
                .setConfig("192.168.200.140", 6666)
                .builder();
        System.out.println(helper.doGet("http://192.168.100.194:8080/header").respStr());
    }

}

