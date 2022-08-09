package com.qring.common.base.lock.annotation;

import com.qring.common.base.lock.executor.LockExecutor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Qring
 * @Description 分布式锁注解
 * @Date 2022/8/9 9:26
 * @Version 1.0
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface QringLock {
    /**
     * 未设置默认 包名 + 类名 + 方法名
     * @return 锁名称
     */
    String key() default "";

    /**
     * 未设置默认 按照注入顺序获取第一个
     * @return 锁执行器
     */
    Class<? extends LockExecutor> executor() default LockExecutor.class;

    /**
     * 未设置默认 30s, 单位: 毫秒
     * @return 锁过期时间
     */
    long expireTime() default -1;

    /**
     * 未设置默认不等待, 单位: 毫秒
     * @return 获取锁等待时间
     */
    long awaitTime() default -1;

    /**
     * 业务方法执行完后自动释放锁, 如果为 false, 锁在达到过期时间才会释放
     * @return 是否自动释放锁
     */
    boolean autoRelease() default true;
}
