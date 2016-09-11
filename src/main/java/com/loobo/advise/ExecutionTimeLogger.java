package com.loobo.advise;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class ExecutionTimeLogger {
    private Logger logger;

    public ExecutionTimeLogger() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Around("@annotation(com.loobo.advise.LoggingTime)")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch sw = new StopWatch();
        try {
            sw.start();
            return joinPoint.proceed();
        } finally {
            sw.stop();
            logger.info(joinPoint.getSignature().getDeclaringTypeName()
                    + "." + joinPoint.getSignature().getName() + " cost " + sw.getTotalTimeMillis() + "ms");
        }
    }
}


