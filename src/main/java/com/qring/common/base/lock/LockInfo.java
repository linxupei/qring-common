package com.qring.common.base.lock;

import com.qring.common.base.lock.executor.LockExecutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Qring
 * @Description 锁信息
 * @Date 2022/8/9 11:01
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockInfo {
    /**
     * 锁名称
     */
    private String lockKey;

    /**
     * 锁值
     */
    private String lockValue;

    /**
     * 过期事件
     */
    private Long expireTime;

    /**
     * 获取锁等待时间
     */
    private Long awaitTime;

    /**
     * 锁重试间隔
     */
    private Long retryInterval;

    /**
     * 获取锁次数
     */
    private Integer acquireCount;

    /**
     * 锁实例
     */
    private Object lockInstance;

    /**
     * 锁执行器
     */
    private LockExecutor lockExecutor;
}
