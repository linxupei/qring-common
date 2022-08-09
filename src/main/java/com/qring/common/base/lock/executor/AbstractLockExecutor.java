package com.qring.common.base.lock.executor;

/**
 * @Author Qring
 * @Description 抽象锁执行器
 * @Date 2022/8/9 14:05
 * @Version 1.0
 */
public abstract class AbstractLockExecutor<T> implements LockExecutor<T> {

    protected T obtainLockInstance(boolean locked, T lockInstance) {
        return locked ? lockInstance : null;
    }

}
