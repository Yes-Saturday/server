package com.saturday.common.aspect;

import com.saturday.common.annotation.TimePass;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class TimePassAspect {

    private final static Logger log = LoggerFactory.getLogger(TimePassAspect.class);

    @Around("@within(timePass) || @annotation(timePass)")
    public Object before(ProceedingJoinPoint joinPoint, TimePass timePass) throws Throwable {

        if (timePass == null)
            timePass = joinPoint.getTarget().getClass().getAnnotation(TimePass.class);

        TimeUnit timer = TimeUnit.MILLISECONDS;
        if (timePass != null)
            timer = timePass.value();

        long time = System.nanoTime();
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } finally {
            time = System.nanoTime() - time;
            log.info("Method : [{}.{}], time : {}({})",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    joinPoint.getSignature().getName(),
                    timer.convert(time, TimeUnit.NANOSECONDS),
                    timer.name());

        }
    }
}