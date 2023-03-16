package com.qring.common.test.retry;

import cn.hutool.core.util.NumberUtil;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Qring
 * @Description 重试测试
 * @Date 2022/10/11 15:20
 * @Version 1.0
 */
@Slf4j
public class RetryTest {
    static int index = 1;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        List<T> list = Arrays.asList(new T(1, 2), new T(2, 3), new T(0, 1));
        BigDecimal a1 = new BigDecimal("2000.0");
        BigDecimal a2 = new BigDecimal("2000");
        System.out.println(NumberUtil.equals(a1, a2));
        System.out.println(a1.equals(a2));
        System.out.println(a1.compareTo(a2));
    }


    public static void addJob() {
        throw new RuntimeException("?");
    }

    @Data
    @AllArgsConstructor
    static class T {
        int i;
        int j;
    }

    @Test
    public void test() {
        Retryer<T> retryer = RetryerBuilder.<T>newBuilder()
                .retryIfResult(res -> !(res.i == res.j))
                .withWaitStrategy(WaitStrategies.fibonacciWait())
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .build();

        try {
            AtomicInteger j = new AtomicInteger(0);
            T flag = retryer.call(() -> {
                j.getAndIncrement();
                return new T(5, j.get());
            });
            System.out.println(flag);
        } catch (Exception e) {
            System.out.println("??");
            throw new RuntimeException(e);
        }
    }
}
