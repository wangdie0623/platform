package com.wang.platform.crawler;

public class ResponseNotSuccessException extends RuntimeException {
    public ResponseNotSuccessException() {
        super("响应未成功");
    }
    public ResponseNotSuccessException(String msg) {
        super(msg);
    }
}
