package com.wang.platform.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(100)
@Slf4j
public class ControllerLogAspect {
    @Value("${spring.application.name}")
    private String appName;

    @Around("execution(* com.wang.platform..*.controller..*(..))")
    public Object error(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String methodName = methodSignature.getMethod().getName();
        String clazzName = pjp.getTarget().getClass().getName();
        Object[] args = pjp.getArgs();
        log.info("{},{}.{},params:{}",
                appName, clazzName, methodName,
                JSON.toJSONString(args));
        Object result = pjp.proceed();
        log.info("result:{}", JSON.toJSONString(result));
        return result;
    }
}
