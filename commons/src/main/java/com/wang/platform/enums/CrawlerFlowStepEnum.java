package com.wang.platform.enums;

/**
 * 爬虫需交互流程
 */
public enum CrawlerFlowStepEnum {
    LOGIN_INFO,//登录参数 创建爬虫对象
    IMG_CODE,//图片验证码  更新爬虫图片验证码
    QR_CODE,//二维码 更新爬虫二维码图片
    PHONE_CODE,//短信验证码 更新短信验证码
    LOGIN//登录 提交登录
}
