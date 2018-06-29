package com.wang.platform.plugins;

import com.wang.platform.beans.AuthInfo;
import com.wang.platform.beans.ResultInfo;

public interface IWebsitePlugin {

    ResultInfo loadingLoginInfo(AuthInfo authInfo);

    /**
     * 刷新验证码图片
     * @return
     */
    ResultInfo restImgCode();

    /**
     * 刷新二维码图片
     * @return
     */
    ResultInfo restQRCode();

    /**
     * 刷新短信码
     * @return
     */
    ResultInfo restPhoneCode();
    /**
     * 登录
     *
     * @return
     */
    ResultInfo login(AuthInfo authInfo);

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
     * 执行完整采集流程
     */
    void execute(AuthInfo authInfo);

    /**
     * 释放资源
     */
    void closeAll();
}
