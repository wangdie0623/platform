package com.wang.platform.message.aspect;


import com.wang.platform.beans.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(100)
@Slf4j
public class BaseAspect {
    @Value("${spring.application.name}")
    private String appName;

    @Around("execution(* com.wang.platform.message.controller.*.*(..))")
    public Object error(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (Exception e) {
            log.error(appName + "异常", e);
            return ResultInfo.fail(appName + "服务器异常");
        }
    }


}
