package com.wang.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CrawlerCodeEnum {
    /**
     * 6-开头 主要流程
     */
    LOGIN_INFO_FAIL("6099"), LOGIN_INFO_SUCCESS("6000"),
    LOGIN_FAIL("6199"), LOGIN_SUCCESS("6100"),
    COLLECT_FAIL("6299"), COLLECT_SUCCESS("6200"),
    FORMAT_FAIL("6399"), FORMAT_SUCCESS("6300"),
    SAVE_FAIL("6499"), SAVE_SUCCESS("6400"),
    OTHER_FAIL("9999"), INFO("0000"),
    /**
     * 3-开头 流程控制
     */
    CODE_IMG_FAIL("3199"), CODE_IMG_SUCCESS("3100"),
    PHONE_MSG_FAIL("3299"), PHONE_MSG_SUCCESS("3200"),
    QCODE_IMG_FAIL("3399"), QCODE_IMG_SUCCESS("3300"),
    CONTROL_ERROR("3999");

    private String code;
}
