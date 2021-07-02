package com.samsung.erp.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return proceed;
    }

    @Pointcut("execution(* com.samsung.erp.services.*.*(..))")
    private void pcMethod() {}

    @Before("pcMethod()")
    private void beforeMethod() {
        System.out.println("Going to run execution(* com.samsung.erp.services.*.*(..)).");
    }
}

