package com.qring.common.test.repository;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/13 9:20
 * @Version 1.0
 */
@Component
@EnableScheduling
@EnableAsync
public class TestComponent {
    private List<String> list;

    private CyclicBarrier cyclicBarrier;

    @PostConstruct
    public void init() {
        list = new ArrayList<>();
        cyclicBarrier = new CyclicBarrier(2);
    }

    @Async
    @Scheduled(fixedDelay = 1000)
    public void first() throws BrokenBarrierException, InterruptedException {
        cyclicBarrier.await();
        for (int i = 0; i < 100; i++) {
            list.add("1");
        }
        System.out.println(">>>>>>>>>>> first end: " + list.size());
    }

    @Async
    @Scheduled(fixedDelay = 1000)
    public void second() throws BrokenBarrierException, InterruptedException {
        cyclicBarrier.await();
        for (int i = 0; i < 100; i++) {
            list.add("2");
        }
        System.out.println(">>>>>>>>>>> second end: " + list.size());
    }

    @Async
    @Scheduled(fixedDelay = 1000)
    public void third() {
        System.out.println(">>>>>>>>>>> third start");
        System.out.println(">>>>>>>>>>" + list.size());
        System.out.println(">>>>>>>>>>> third end");
    }
}
