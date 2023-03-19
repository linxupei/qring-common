package com.qring.common.test.quartz;

import com.qring.common.test.QringCommonTestApplication;
import com.qring.common.test.common.quartz.QuartzTaskUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/8 10:01
 * @Version 1.0
 */
@Slf4j
@SpringBootTest(classes = QringCommonTestApplication.class)
class QuartzTaskUtilTest {
    @Resource
    private QuartzTaskUtil quartzTaskUtil;
    @Resource
    private SchedulerFactoryBean schedulerFactoryBean;

    @Test
    void addJob() throws SchedulerException {
    }

    @Test
    void updateScheduleJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        System.out.println(scheduler.checkExists(JobKey.jobKey("j11", "j11")));
        System.out.println(scheduler.getTriggersOfJob(JobKey.jobKey("j11", "j11")));
        quartzTaskUtil.removeJob("j11", "j11");
        System.out.println(scheduler.checkExists(JobKey.jobKey("j11", "j11")));
        System.out.println(scheduler.getTriggersOfJob(JobKey.jobKey("j11", "j11")));
    }

    @Test
    void removeJob() throws SchedulerException {
        quartzTaskUtil.removeJob("j1", "j1g");
    }

    @Test
    void pause() {
    }

    @Test
    void resume() {
    }

    @Test
    void startJobs() {
    }

    @Test
    void shutdownJobs() {
    }
}