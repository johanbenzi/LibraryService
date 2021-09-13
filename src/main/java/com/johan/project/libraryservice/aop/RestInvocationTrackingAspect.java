package com.johan.project.libraryservice.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Log4j2
public class RestInvocationTrackingAspect {
    @Around("controller() && allMethod()")
    public Object trackRestCall(final ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();
        final String className = joinPoint.getSignature().getDeclaringTypeName();
        final String methodName = joinPoint.getSignature().getName();
        try {
            final Object ret = joinPoint.proceed();
            final long executionTime = System.currentTimeMillis() - start;
            log.info(String.format("Success in %s.%s - executed in %d ms", className, methodName, executionTime));
            return ret;
        } catch (final Throwable e) {
            final long executionTime = System.currentTimeMillis() - start;
            log.error(String.format("Failure in %s.%s - executed in %d ms", className, methodName, executionTime));
            throw e;
        }
    }

    @Pointcut("within(@com.johan.project.libraryservice.rest.LibraryBaseController *)")
    public void controller() {
    }

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
    }
}
