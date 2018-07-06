package com.wang.platform.plugins.utils;

import com.wang.platform.crawler.HttpHelperBuilder;
import com.wang.platform.crawler.IHttpHelper;
import com.wang.platform.plugins.annotations.Site;
import com.wang.platform.plugins.enums.SiteClientEnums;
import com.wang.platform.plugins.proxy.HttpHelperProxy;
import com.wang.platform.plugins.website.IWebsitePlugin;
import com.wang.platform.utils.AnnotationsSearch;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.List;

@Slf4j
public class WebsiteFactory {
    //站点类清单
    private static final List<Class> classes = AnnotationsSearch
            .searchAnnotationClass(IWebsitePlugin.class.getPackage().getName(), Site.class);

    /**
     * 获取站点对应对象
     *
     * @param name
     * @return
     */
    public static IWebsitePlugin getSite(String name) {
        IWebsitePlugin bean = null;
        for (Class item : classes) {
            Site site = (Site) item.getAnnotation(Site.class);
            if (site.value().equalsIgnoreCase(name)) {
                try {
                    Constructor constructor = item.getConstructor(new Class[]{IHttpHelper.class});
                    bean = (IWebsitePlugin) constructor.newInstance(getHelper(name));
                    break;
                } catch (Exception e) {
                    log.error("创建站点对象异常", e);
                }
            }
        }
        return bean;
    }

    /**
     * 获取name对应httpHelper对象
     *
     * @param name
     * @return
     */
    private static IHttpHelper getHelper(String name) {
        SiteClientEnums book17k = SiteClientEnums.getEnum(name);
        CloseableHttpClient client = book17k.getClient();
        IHttpHelper target = HttpHelperBuilder.builderDefault(client);
        HttpHelperProxy proxy = new HttpHelperProxy(target);
        IHttpHelper helper = (IHttpHelper) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(),
                proxy);
        return helper;
    }


}