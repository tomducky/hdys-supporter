package com.carry.www.aop.aop;


import com.carry.www.aop.log.AopLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类描述：AOP
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020/7/10 9:03
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Component
@Aspect
public class SelfAspect {
    private final Logger log = LoggerFactory.getLogger(SelfAspect.class);

    @Autowired
    private AopLog aopLog;

    /**
     * @方法描述: @annotation匹配注解有aopControllerLog注解的方法
     * @Param: []
     * @return: void
     * @Author: carry
     */
    @Pointcut("@annotation(com.carry.www.aop.targetion.AopControllerLog)")
    public void aspect() {
    }

    @Before("aspect()")
    public void beforeMethod(JoinPoint joinPoint) {
        log.info("=====================开始执行前置通知==================");

        try {
            aopLog.proceeAopLog(joinPoint, "前置通知", "info", null);
        } catch (Throwable e) {
            log.info("around " + joinPoint + " with exception : " + e.getMessage());
        }

        log.info("=====================前置通知执行完毕==================");
    }

    /**
     * @方法描述: 异常通知
     * @Param: [joinPoint, ex]
     * @return: void
     * @Author: carry
     */
    @AfterThrowing(pointcut = "aspect()", throwing = "ex")
    public void afterThrow(JoinPoint joinPoint, Exception ex) {
        log.info("=====================开始执行异常通知==================");

        try {
            aopLog.proceeAopLog(joinPoint, "异常通知", "error", ex.getMessage());
        } catch (Throwable e) {
            log.info("around " + joinPoint + " with exception : " + e.getMessage());
        }

        log.info("=====================异常通知执行完毕==================");
    }

}
