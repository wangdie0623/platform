package com.wang.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CrawlerCodeEnum {
    LOGIN_INFO_FAIL("6099"), LOGIN_INFO_SUCCESS("6000"),
    LOGIN_FAIL("6199"), LOGIN_SUCCESS("6100"),
    COLLECT_FAIL("6299"), COLLECT_SUCCESS("6200"),
    FORMAT_FAIL("6399"), FORMAT_SUCCESS("6300"),
    SAVE_FAIL("6499"), SAVE_SUCCESS("6400"),
    OTHER_FAIL("9999"), INFO("0000");
    private String code;
}
