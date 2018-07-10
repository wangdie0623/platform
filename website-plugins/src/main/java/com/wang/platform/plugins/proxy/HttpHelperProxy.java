package com.wang.platform.plugins.proxy;

import com.wang.platform.beans.ResultInfo;
import com.wang.platform.crawler.CookieUtils;
import com.wang.platform.crawler.IHttpHelper;
import com.wang.platform.enums.CrawlerCodeEnum;
import com.wang.platform.exceptions.ServiceException;
import com.wang.platform.plugins.website.CookieCacheUtils;
import com.wang.platform.plugins.website.TokenInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.slf4j.MDC;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
//代理IHttpHelper对象
public class HttpHelperProxy implements InvocationHandler {
    private Object target;//被代理对象

    public HttpHelperProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.getName().startsWith("do")) {
            return method.invoke(target, args);
        }
        IHttpHelper helper = (IHttpHelper) target;
        //获取线程token
        String token = MDC.get("token");
        try {
            //从redis获取token关联cookie集合
            Set<Cookie> cookieSet = CookieCacheUtils.getCookieSet(token);
            //将cookie集合转为字符串
            String cookieStr = CookieUtils.setToStr(cookieSet);
            //添加cookie到请求
            helper.addHeader("cookie", cookieStr);
            //执行请求
            Object result = method.invoke(helper, args);
            //获取新的响应头集合
            Header[] respHeaders = helper.getRespHeaders();
            //生成新cookie集合
            Set<Cookie> newCookieSet = CookieUtils.newCookies(cookieSet, respHeaders);
            //保存新token关联cookie集合到redis
            CookieCacheUtils.cacheCookieSet(token, newCookieSet);
            return result;
        } catch (Exception e) {
            log.error("爬虫工具代理异常", e);
            throw new ServiceException("爬虫工具代理异常");
        }
    }


}
