package com.qring.common.test.quartz;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.PostConstruct;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/8 10:06
 * @Version 1.0
 */
@Slf4j
public class HelloJob extends QuartzJobBean {

    private static final int MAX_RETRY = 5;
    private Retryer<Boolean> retryer;
    @PostConstruct
    public void initRetryer() {
        retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(res -> !res)
                .withWaitStrategy(WaitStrategies.fibonacciWait())
                .withStopStrategy(StopStrategies.stopAfterAttempt(MAX_RETRY))
                .build();
    }


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    }

    public void addJob() {
        System.out.println("1");
    }
}
