package com.qring.lock.executor;

/**
 * @Author Qring
 * @Description 分布式锁执行器
 * @Date 2022/8/9 9:48
 * @Version 1.0
 */
public interface LockExecutor<T> {

    /**
     * 锁续期, 目前仅有 redisson 支持, 且 expire 参数为 -1 才会自动续期
     * @return 默认不进行自动续期
     */
    default boolean renewal() {
        return false;
    }

    /**
     * 加锁
     * @param lockKey       锁标识
     * @param lockValue     锁值
     * @param expireTime    锁有效时间
     * @param awaitTime     获取锁等待时间
     * @return 锁信息
     */
    T lock(String lockKey, String lockValue, long expireTime, long awaitTime);

    /**
     * 解锁
     * 解锁需要验证lockValue
     * 客户端 A 加锁, 一段时间后, 客户端 A 解锁, 在执行 unLock 之前锁失效了,
     * 此时客户端 B 尝试加锁成功, 然后客户端 B 再执行 unLock 方法, 则将客户端 B 的锁解除了
     * @param lockKey       锁标识
     * @param lockValue     锁值
     * @param lockInstance  锁实例
     * @return 是否解锁成功
     */
    boolean unLock(String lockKey, String lockValue, T lockInstance);
}
