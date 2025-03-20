package ru.lytvenkovmv.loggingstarter.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class LoggingAspect {
    Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(ru.lytvenkovmv.loggingstarter.annotation.LogExecutionTime)")
    @Pointcut()
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            throw e.getCause();
        } finally {
            long stop = System.nanoTime();
            log.info("Время выполнения метода {}(): {} мкс", joinPoint.getSignature().getName(), (stop - start) / 1000);
        }
    }
}
