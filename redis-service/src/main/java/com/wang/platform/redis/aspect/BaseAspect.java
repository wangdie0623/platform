package com.wang.platform.redis.aspect;


import com.wang.platform.beans.ResultInfo;
import com.wang.platform.exceptions.ServiceException;
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

    @Around("execution(* com.wang.platform.redis.controller.*.*(..))")
    public Object error(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (ServiceException e) {
            log.error(appName + "服务层异常", e);
            return ResultInfo.fail(e.getMsg());
        } catch (Exception e) {
            log.error(appName + "异常", e);
            return ResultInfo.fail(appName + "服务器异常");
        }
    }


}
