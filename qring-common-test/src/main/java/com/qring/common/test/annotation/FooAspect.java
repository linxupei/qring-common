package com.qring.common.test.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/12/14 16:47
 * @Version 1.0
 */
@Component
@Aspect
public class FooAspect {

//    @Resource
//    private Environment environment;
//
//    @Pointcut("@annotation(com.qring.common.test.annotation.Foo)")
//    public void point(){}
//
//    @Around("@annotation(point)")
//    public Object setValue(ProceedingJoinPoint pjp, Foo foo) {
//        Object result = null;
//        String groupId = environment.resolvePlaceholders(foo.cron());
//        foo.
//        return result;
//    }
}
