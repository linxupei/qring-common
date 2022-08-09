package com.qring.common.base.lock;

import com.qring.common.base.lock.autoconfig.LockProperties;
import com.qring.common.base.lock.exception.LockException;
import com.qring.common.base.lock.executor.LockExecutor;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author Qring
 * @Description 锁模板
 * @Date 2022/8/9 11:10
 * @Version 1.0
 */
@SuppressWarnings("rawtypes")
public class LockTemplate implements InitializingBean {
    private final Map<Class<? extends LockExecutor>, LockExecutor> executorMap = new LinkedHashMap<>();

    @Setter
    private LockProperties properties;

    @Setter
    private List<LockExecutor> executors;

    private LockExecutor primaryExecutor;

    /**
     * 加锁
     * @param lockKey       锁标识
     * @param expireTime    锁过期时间
     * @param awaitTime     获取锁等待时间
     * @param executor      锁执行器
     * @return  锁信息
     */
    public LockInfo lock(String lockKey, long expireTime, long awaitTime, Class<? extends LockExecutor> executor) {
        awaitTime = awaitTime < 0 ? properties.getAwaitTime() : awaitTime;
        long retryInterval = properties.getRetryInterval();
        LockExecutor lockExecutor = obtainExecutor(executor);
        expireTime = !lockExecutor.renewal() && expireTime <= 0 ? properties.getExpireTime() : expireTime;
        int acquireCount = 0;
        String lockValue = UUID.randomUUID().toString().replaceAll("-", "");
        long start = System.currentTimeMillis();
        try {
            do {
                acquireCount++;
                Object lockInstance = lockExecutor.lock(lockKey, lockValue, expireTime, awaitTime);
                if (!ObjectUtils.isEmpty(lockInstance)) {
                    return new LockInfo(lockKey, lockValue, expireTime, awaitTime, retryInterval, acquireCount, lockInstance, lockExecutor);
                }
                TimeUnit.MILLISECONDS.sleep(retryInterval);
            } while (System.currentTimeMillis() - start <= awaitTime);
        } catch (InterruptedException e) {
            throw new LockException(Constant.LOCK_FAILURE_MSG);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public boolean unLock(LockInfo lockInfo) {
        if (ObjectUtils.isEmpty(lockInfo)) {
            return false;
        }
        return lockInfo.getLockExecutor().unLock(lockInfo.getLockKey(), lockInfo.getLockValue(), lockInfo.getLockInstance());
    }

    /**
     * 根据类型获取执行器
     * @param clazz 执行器类型
     * @return 执行器实例
     */
    protected LockExecutor obtainExecutor(Class<? extends LockExecutor> clazz) {
        if (ObjectUtils.isEmpty(clazz) || ObjectUtils.nullSafeEquals(clazz, LockExecutor.class)) {
            return primaryExecutor;
        }
        LockExecutor lockExecutor = executorMap.get(clazz);
        Assert.notNull(lockExecutor, String.format("can not get bean type of %s", clazz));
        return lockExecutor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.isTrue(properties.getAwaitTime() >= 0, "awaitTime must lease 0");
        Assert.isTrue(properties.getExpireTime() >= -1, "expireTime must lease -1");
        Assert.isTrue(properties.getRetryInterval() >= 0, "retryInterval must more than 0");
        Assert.notEmpty(executors, "executors must have at lease one");

        for (LockExecutor executor : executors) {
            executorMap.put(executor.getClass(), executor);
        }

        Class<? extends LockExecutor> primaryExecutor = properties.getPrimaryExecutor();
        if (ObjectUtils.isEmpty(primaryExecutor)) {
            this.primaryExecutor = executors.get(0);
        } else {
            this.primaryExecutor = executorMap.get(primaryExecutor);
            Assert.notNull(this.primaryExecutor, "primaryExecutor must be not null");
        }
    }
}
