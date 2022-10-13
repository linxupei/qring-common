package com.qring.common.test.quartz;

import com.qring.common.test.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/8 10:01
 * @Version 1.0
 */
@Slf4j
@SpringBootTest(classes = Application.class)
class QuartzTaskUtilTest {
    @Resource
    private QuartzTaskUtil quartzTaskUtil;

    @Test
    void addJob() throws SchedulerException {
        quartzTaskUtil.removeJob("j12", null);
        quartzTaskUtil.addJob("j11", null, "g11", "g1g",
                HelloJob.class, CronUtil.getCron(LocalDateTime.now().plusSeconds(10)));
    }

    @Test
    void updateScheduleJob() {

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