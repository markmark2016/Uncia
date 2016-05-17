package com.markeveryday.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 切面日志记录, 对service层的方法调用输出日志
 *
 * @author liming
 */
@Component
@Aspect
public class MarkMarkLogger {

    private static final Logger logger = LoggerFactory.getLogger(MarkMarkLogger.class);


    @Before("execution(* com.markeveryday.service.*.*ServiceImpl.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String signatureName = joinPoint.getSignature().getName();
        logger.info("Calling {}, args:{}", signatureName, args);

    }

    @After("execution(* com.markeveryday.service.*.*ServiceImpl.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String signatureName = joinPoint.getSignature().getName();
        logger.info("Completed call {} !", signatureName);

    }

    @AfterReturning(
            pointcut = "execution(* com.markeveryday.service.*.*ServiceImpl.*(..))",
            returning = "result"
    )
    public void logAfterReturned(JoinPoint joinPoint, Object result) {

        Object[] args = joinPoint.getArgs();
        String signatureName = joinPoint.getSignature().getName();
        logger.info("Completed call {} !, the return value:{}", signatureName, result);
    }


}
