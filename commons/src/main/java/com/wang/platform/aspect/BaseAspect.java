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
//
//    @Around("execution(* com.wang.platform..*.controller..*(..))")
//    public Object error(ProceedingJoinPoint pjp) throws Throwable {
//        try {
//            return pjp.proceed();
//        } catch (ServiceException e) {
//            log.error(appName + "服务层异常", e);
//            return ResultInfo.fail(e.getMsg());
//        } catch (Exception e) {
//            log.error(appName + "异常", e);
//            return ResultInfo.fail(appName + "服务器异常");
//        }
//    }

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
