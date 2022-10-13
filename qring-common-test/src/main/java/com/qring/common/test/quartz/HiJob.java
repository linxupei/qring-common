package com.qring.common.test.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/8 11:00
 * @Version 1.0
 */
@Slf4j
public class HiJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Hi");
        log.error("Hi!");
    }
}
