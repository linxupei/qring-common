package com.qring.lock;

import com.qring.lock.exception.LockException;

import java.lang.reflect.Method;

/**
 * @Author Qring
 * @Description 默认获取锁失败策略
 * @Date 2022/8/9 10:56
 * @Version 1.0
 */
public class DefaultLockFailureStrategy implements LockFailureStrategy {

    @Override
    public void onLockFailure(String key, Method method, Object[] args) {
        throw new LockException(Constant.LOCK_FAILURE_MSG);
    }
}
