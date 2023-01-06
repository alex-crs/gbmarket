package com.gb.market.market.services;

import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Aspect
@Component
public class PerformanceMeter {
    private Map<String, Long> cartServiceTime;
    private Map<String, Long> productServiceTime;
    private Map<String, Long> userServiceTime;

    @PostConstruct
    public void init() {
        userServiceTime = new HashMap<>();
        productServiceTime = new HashMap<>();
        cartServiceTime = new HashMap<>();
    }

    public String getPerformanceProductTime() {
        final long[] totalTime = {0};
        productServiceTime.forEach((key, value) -> totalTime[0] += value);
        return String.format("ProductService: %s ms", totalTime[0]);
    }

    public String getPerformanceCartTime() {
        final long[] totalTime = {0};
        cartServiceTime.forEach((key, value) -> totalTime[0] += value);
        return String.format("CartService: %s ms", totalTime[0]);
    }

    public String getPerformanceUserTime() {
        final long[] totalTime = {0};
        userServiceTime.forEach((key, value) -> totalTime[0] += value);
        return String.format("UserService: %s ms", totalTime[0]);
    }

    public List<String> getTotalPerformanceTimeForAllServices() {
        return List.of(getPerformanceProductTime(), getPerformanceCartTime(), getPerformanceUserTime());
    }


    @Around("execution(public * com.gb.market.market.services.ProductService.*(..))")
    public Object meterProduct(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return getObject(proceedingJoinPoint, productServiceTime);
    }


    @Around("execution(public * com.gb.market.market.services.CartService.*(..))")
    public Object meterCart(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return getObject(proceedingJoinPoint, cartServiceTime);
    }

    @Around("execution(public * com.gb.market.market.services.UserService.*(..))")
    public Object meterUser(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return getObject(proceedingJoinPoint, userServiceTime);
    }

    private Object getObject(ProceedingJoinPoint proceedingJoinPoint, Map<String, Long> serviceTime) throws Throwable {
        String name = proceedingJoinPoint.getStaticPart().getSignature().getName();
        long start = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long totalTime = end - start;
        serviceTime.put(name, totalTime);
        return out;
    }
}
