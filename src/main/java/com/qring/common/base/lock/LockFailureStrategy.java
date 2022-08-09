package com.qring.common.base.lock;

import java.lang.reflect.Method;

/**
 * @Author Qring
 * @Description 锁失败策略
 * @Date 2022/8/9 10:52
 * @Version 1.0
 */
public interface LockFailureStrategy {
    /**
     * 获取锁失败事件
     * @param key       锁标识
     * @param method    执行方法
     * @param args      参数
     */
    void onLockFailure(String key, Method method, Object[] args);
}
