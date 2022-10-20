package com.qring.common.test.retry;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Qring
 * @Description 重试测试
 * @Date 2022/10/11 15:20
 * @Version 1.0
 */
@Slf4j
public class RetryTest {
    static int index = 1;

    @Test
    public void test() {
        Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
                .retryIfResult(res -> !res.equals("true"))
                .withWaitStrategy(WaitStrategies.fibonacciWait())
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .build();

        try {
            String flag = retryer.call(() -> {
                addJob();
                return "true1";
            });
            System.out.println(flag);
        } catch (Exception e) {
            System.out.println("??");
            throw new RuntimeException(e);
        } finally {
            addJob();
        }
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

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        List<T> list = Arrays.asList(new T(1, 2), new T(2, 3), new T(0, 1));
        Map<Integer, Integer> collect = list.stream()
                .filter(t -> t.i > 1)
                .filter(t -> t.j > 1)
                .collect(Collectors.toMap(T::getI, T::getJ));
        System.out.println(collect);
        System.out.println(collect.get(1));
    }
}
