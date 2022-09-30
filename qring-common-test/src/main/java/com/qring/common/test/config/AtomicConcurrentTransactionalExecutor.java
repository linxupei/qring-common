package com.qring.common.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/30 11:43
 * @Version 1.0
 */
@Slf4j
@Component
public class AtomicConcurrentTransactionalExecutor {
    @Resource
    private TransactionalWorker transactionalWorker;

    public boolean execute(ExecutorService executorService, Runnable... runnables) {
        int threadSize = runnables.length;
        CyclicBarrier workerCyclicBarrier = new CyclicBarrier(threadSize);
        AtomicInteger successCounter = new AtomicInteger(threadSize);
        for (Runnable runnable : runnables) {
            executorService.submit(() -> {
                try {
                    transactionalWorker.run(workerCyclicBarrier, successCounter, runnable);
                } catch (Exception e) {
                    log.error("TransactionalWorker current thread execute error before runnable.run！", e);
                }
            });
        }
        return successCounter.get() == 0;
    }
}
