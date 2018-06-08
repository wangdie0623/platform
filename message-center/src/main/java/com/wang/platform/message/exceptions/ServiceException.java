package com.wang.platform.message.exceptions;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private String msg;

    public ServiceException() {
    }

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
