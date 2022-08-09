package com.qring.lock.executor;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author Qring
 * @Description Redisson执行器
 * @Date 2022/8/9 15:10
 * @Version 1.0
 */
@RequiredArgsConstructor
public class RedissonLockExecutor extends AbstractLockExecutor<RLock> {
    private final RedissonClient redissonClient;

    @Override
    public boolean renewal() {
        return true;
    }

    @Override
    public RLock lock(String lockKey, String lockValue, long expireTime, long awaitTime) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            boolean isLock = lock.tryLock(awaitTime, expireTime, TimeUnit.MILLISECONDS);
            return obtainLockInstance(isLock, lock);
        } catch (InterruptedException e) {
            return null;
        }
    }

    @Override
    public boolean unLock(String lockKey, String lockValue, RLock lockInstance) {
        if (lockInstance.isHeldByCurrentThread()) {
            try {
                return lockInstance.forceUnlockAsync().get();
            } catch (ExecutionException | InterruptedException e) {
                return false;
            }
        }
        return false;
    }
}
