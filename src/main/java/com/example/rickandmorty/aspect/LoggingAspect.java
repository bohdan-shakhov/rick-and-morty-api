package com.example.rickandmorty.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public LoggingAspect(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {

    }

    @Pointcut("within(com.example.rickandmorty.controller..*)")
    public void applicationPackagePointcut() {

    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() message = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getMessage() != null ? e.getMessage() : "NULL");
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.info("request={}, response={}, traceUID={}, time={} ms",
                        request.getRequestURL(),
                        result,
                        response.getHeader("traceUID"),
                        System.currentTimeMillis() - start);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw new RuntimeException(e.getCause());
        }
    }

    @Before("applicationPackagePointcut() && springBeanPointcut()")
    public void setTraceUID() {
        try {
            response.setHeader("traceUID", UUID.randomUUID().toString());
        } catch (IllegalArgumentException e) {
            log.error("Error while adding UID to response for request" + request.getRequestURL());
            throw new RuntimeException(e.getCause());
        }
    }
}

