package com.qring.common.base.lock.aop;

import com.qring.common.base.lock.Constant;
import com.qring.common.base.lock.LockFailureStrategy;
import com.qring.common.base.lock.LockInfo;
import com.qring.common.base.lock.LockTemplate;
import com.qring.common.base.lock.annotation.QringLock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @Author Qring
 * @Description 锁拦截器
 * @Date 2022/8/9 10:16
 * @Version 1.0
 */
@Slf4j
@AllArgsConstructor
public class LockInterceptor implements MethodInterceptor {
    private final LockTemplate lockTemplate;
    private final LockFailureStrategy lockFailureStrategy;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 避免出现重复执行
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(invocation.getThis());
        if (!ObjectUtils.nullSafeEquals(clazz, invocation.getThis().getClass())) {
            return invocation.proceed();
        }

        QringLock lock = invocation.getMethod().getAnnotation(QringLock.class);
        LockInfo lockInfo = null;
        try {
            String key = StringUtils.hasText(lock.key()) ? lock.key() :
                    invocation.getMethod().getDeclaringClass().getName() + invocation.getMethod().getName();
            lockInfo = lockTemplate.lock(key, lock.expireTime(), lock.awaitTime(), lock.executor());
            if (!ObjectUtils.isEmpty(lockInfo)) {
                return invocation.proceed();
            }
            // lock failure
            lockFailureStrategy.onLockFailure(key, invocation.getMethod(), invocation.getArguments());
            return null;
        } finally {
            if (!ObjectUtils.isEmpty(lockInfo) && lock.autoRelease()) {
                boolean release = lockTemplate.unLock(lockInfo);
                if (!release) {
                    log.error(Constant.UNLOCK_FAILURE_MSG + "lockKey={}, lockValue={}",
                            lockInfo.getLockKey(), lockInfo.getLockValue());
                }
            }
        }
    }
}
