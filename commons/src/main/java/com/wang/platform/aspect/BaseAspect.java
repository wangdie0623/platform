package com.wang.platform.aspect;


import com.wang.platform.beans.ResultInfo;
import com.wang.platform.exceptions.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestControllerAdvice
public class BaseAspect {
    @Value("${spring.application.name}")
    private String appName;


    @ExceptionHandler(ServiceException.class)
    public ResultInfo handServiceException(ServiceException exception, HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error(String.format("%s 调用url：[%s]发生异常.", appName, uri), exception);
        return ResultInfo.fail(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultInfo handException(Exception exception, HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error(String.format("%s 调用url：[%s]发生异常.", appName, uri), exception);
        return ResultInfo.fail("执行失败");
    }

}
