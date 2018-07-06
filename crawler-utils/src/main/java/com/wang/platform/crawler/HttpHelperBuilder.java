package com.wang.platform.crawler;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.concurrent.ConcurrentHashMap;

public class HttpHelperBuilder {

    private ConcurrentHashMap<String, String> defaultHeaders;
    private CookieStore cookieStore;
    private RequestConfig config;

    private HttpHelperBuilder() {
    }

    public IHttpHelper builder() {
        SimpleHttpHelper result = null;
        if (cookieStore != null) {
            result = new SimpleHttpHelper(new CustomCookieStore());
        } else {
            result = new SimpleHttpHelper(new CustomCookieStore());
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
        return new SimpleHttpHelper(HttpPoolFactory.getHttpClient());
    }

    public static IHttpHelper builderDefault(CloseableHttpClient client) {
        return new SimpleHttpHelper(client);
    }

    public static HttpHelperBuilder custom() {
        return new HttpHelperBuilder();
    }

    public HttpHelperBuilder setCookie(CookieStore cookie) {
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


}

