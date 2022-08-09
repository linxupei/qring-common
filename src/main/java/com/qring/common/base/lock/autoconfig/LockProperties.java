package com.qring.common.base.lock.autoconfig;

import com.qring.common.base.lock.executor.LockExecutor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author Qring
 * @Description Lock属性
 * @Date 2022/8/9 10:23
 * @Version 1.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "qring.lock")
public class LockProperties {
    /**
     * 锁过期时间, 未设置默认 30s, 单位: 毫秒
     */
    private long expireTime = 30000L;

    /**
     * 获取锁等待时间, 未设置默认不等待, 单位: 毫秒
     */
    private long awaitTime = 0L;

    /**
     * 获取锁重试时间间隔, 单位: 毫秒
     */
    private long retryInterval = 1000L;

    /**
     * 未设置默认 按照注入顺序获取第一个
     */
    private Class<? extends LockExecutor> primaryExecutor;
}
