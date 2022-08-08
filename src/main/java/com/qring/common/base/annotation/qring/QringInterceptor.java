package com.qring.common.base.annotation.qring;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/8/8 16:53
 * @Version 1.0
 */
@Slf4j
public class QringInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.error("*******************" + invocation.getMethod().getName());
        Qring qring = invocation.getMethod().getAnnotation(Qring.class);
        log.error(qring.name());
        return invocation.proceed();
    }
}
