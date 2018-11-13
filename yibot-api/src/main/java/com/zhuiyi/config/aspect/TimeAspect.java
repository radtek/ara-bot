package com.zhuiyi.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author code-magic
 * @version 1.0
 * date: 2018/07/13
 * description:
 * own: zhuiyi
 */

@Aspect
@Component
@Slf4j
public class TimeAspect {
    @Around("execution(* com.zhuiyi.api..*(..))")
    public Object method(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        log.info("[SYS][REQ][" + pjp.getStaticPart().toString() + "]");
        log.info("[SYS][PARAM]{}", Arrays.toString(args));
        long start = System.currentTimeMillis();
        Object object = pjp.proceed();
        log.info("[SYS][RES][" + pjp.getStaticPart().toString() + "][responseVO: " + object.toString() + "]");
        log.info("[SYS][ASP][TimeAspect][COST: {} ms]", System.currentTimeMillis() - start);
        return object;
    }
}
