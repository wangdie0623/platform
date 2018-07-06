package com.wang.platform.plugins.proxy;

import com.wang.platform.beans.ResultInfo;
import com.wang.platform.crawler.IHttpHelper;
import com.wang.platform.exceptions.ServiceException;
import com.wang.platform.plugins.website.CookieCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.slf4j.MDC;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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

        try {
            String token = MDC.get("token");
            String cookies = CookieCacheUtils.getCookieStr(token);
            helper.addHeader("cookie", cookies);
            Object result = method.invoke(helper, args);
            Header[] respHeaders = helper.getRespHeaders();
            CookieCacheUtils.cacheCookieStr(token, cookies + ";");
            return result;
        } catch (ServiceException e) {
            log.error("爬虫工具代理自定义异常", e);
            return ResultInfo.fail(e.getMsg());
        } catch (Exception e) {
            log.error("爬虫工具代理异常", e);
        }
        return null;
    }


}
