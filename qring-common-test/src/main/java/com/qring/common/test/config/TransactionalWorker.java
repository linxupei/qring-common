package com.qring.common.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/30 11:42
 * @Version 1.0
 */
@Component
@Slf4j
public class TransactionalWorker {

    /**
     * @param workerCyclicBarrier 用于控制多个线程同时结束
     * @param successCounter 线程任务执行记录器
     * @param runnable 执行的任务
     */
    @Transactional(rollbackFor = Exception.class)
    public void run(CyclicBarrier workerCyclicBarrier, AtomicInteger successCounter, Runnable runnable) {
        boolean isSuccess = false;
        try {
            runnable.run();
            successCounter.decrementAndGet();
            isSuccess = true;
        } catch (Exception e) {
            log.error("TransactionalWorker current thread execute error！", e);
            throw e;
        } finally {
            try {
                // 如果是数据库操作慢导致长时间阻塞，并不会被线程池中断（Interrupt），也就是会等到数据库操作完成之后，进入到这一步，然后直接报超时异常
                workerCyclicBarrier.await();
            } catch (Exception e) {
                // 等待其他线程时超时
                log.error("TransactionalWorker current thread execute CyclicBarrier.await error!", e);
                if (isSuccess) {
                    // 要回滚计数，否则：假设全部线程都操作成功，但刚好超时，主线程shutdown线程池后，计数为0，会返回成功
                    successCounter.incrementAndGet();
                }
            }
        }
        if (successCounter.get() != 0) {
            throw new RuntimeException("TransactionalWorker other thread execute error, create SystemException to rollback！");
        }
    }
}
