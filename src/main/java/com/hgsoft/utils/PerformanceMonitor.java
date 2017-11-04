package com.hgsoft.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @Author: 吴锡霖
 * @File: PerformanceMonitor.java
 * @DATE: 2015/12/10
 * @TIME: 12:54
 */
@Aspect
/*@Component*/
public class PerformanceMonitor {

    private static Logger logger = LoggerFactory.getLogger(PerformanceMonitor.class);
    
    private Long warnTime;
    /**
     * 监控service包及其子包的所有public方法
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    @Pointcut("execution(* com.hgsoft.*.serviceInterface..*.*(..))")
    private void pointCutMethod() {
    }

    //声明环绕通知
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        String uuid = UUID.randomUUID().toString();
        logger.debug("{} {}{}{} ", uuid, pjp.getTarget().getClass(), ".", pjp
                .getSignature().getName());
		long begin = System.nanoTime();
		Object o = pjp.proceed();
		long end = System.nanoTime();
        long time = (end - begin) / 1000000;
		logger.debug("{} {}{}{} 耗时: {}ms", uuid, pjp.getTarget().getClass(), ".", pjp
				.getSignature().getName(), time);
        if (time > warnTime) {
            logger.warn("{} {}{}{} 耗时: {}ms，超过设置的时间：{}ms", uuid, 
                    pjp.getTarget().getClass(), ".", pjp
                    .getSignature().getName(), time, warnTime);
        }
		return o;
    }

    public void setWarnTime(Long warnTime) {
        this.warnTime = warnTime;
    }
}
