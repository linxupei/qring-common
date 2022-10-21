package com.qring.common.test.repository;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
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
}
