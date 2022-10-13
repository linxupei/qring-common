package com.qring.common.test.retry;

import com.github.rholder.retry.*;
import com.qring.common.test.Application;
import com.qring.common.test.exception.JobExistException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Nonnull;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * @Author Qring
 * @Description 重试测试
 * @Date 2022/10/11 15:20
 * @Version 1.0
 */
@Slf4j
@SpringBootTest(classes = Application.class)
public class RetryTest {
    static int index = 1;

    @Test
    public void test() {
        Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
                .retryIfResult(res -> !res.equals("true"))
                .retryIfExceptionOfType(JobExistException.class)
                .withWaitStrategy(WaitStrategies.fibonacciWait())
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .build();

        try {
            String flag = retryer.call(() -> {
                addJob(null);
                return "true1";
            });
            System.out.println(flag);
        } catch (ExecutionException | RetryException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addJob(@Nonnull Integer i) {

    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ConcurrentHashMap<String, Boolean> resMap = new ConcurrentHashMap<>();
        resMap.forEach((k, v) -> {
            System.out.println(k);
        });
    }
}
