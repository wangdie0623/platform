package com.wang.platform.plugins.website;

import com.wang.platform.beans.BaseCrawlerParam;
import com.wang.platform.beans.CrawlerLoginParam;
import com.wang.platform.beans.ResultInfo;

public interface IWebsitePlugin {


    /**
     * 刷新验证码图片
     *
     * @return
     */
    ResultInfo restImgCode(BaseCrawlerParam param);

    /**
     * 刷新二维码图片
     *
     * @return
     */
    ResultInfo restQRCode(BaseCrawlerParam param);

    /**
     * 刷新短信码
     *
     * @return
     */
    ResultInfo restPhoneCode(BaseCrawlerParam param);

    /**
     * 获取登录消息
     *
     * @param param
     * @return
     */
    ResultInfo loginInfo(BaseCrawlerParam param);

    /**
     * 登录
     *
     * @return
     */
    ResultInfo login(CrawlerLoginParam param);

    /**
     * 采集
     *
     * @return
     */
    ResultInfo collect();


    /**
     * 解析格式化
     *
     * @return
     */
    ResultInfo format();

    /**
     * @return
     */
    ResultInfo saveData();


    /**
     * 释放资源
     */
    void closeAll();


}
