package com.UnivTime.global.aspect;

import com.UnivTime.global.annotation.TimeTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeAspect {

    @Around("@annotation(timeTrace)")
    public Object doTimeLog(ProceedingJoinPoint joinPoint, TimeTrace timeTrace) throws Throwable {

        String methodName = timeTrace.methodName();
        if (methodName.isEmpty()) {
            methodName = joinPoint.getSignature().getName();
        }

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("[TimeLog] {} - {}: {} ms",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                methodName,
                resultTime);
        return result;
    }
}
