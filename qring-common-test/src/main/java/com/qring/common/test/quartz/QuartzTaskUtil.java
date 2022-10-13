package com.qring.common.test.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Qring
 * @Description 定时任务服务类
 * @Date 2022/10/8 8:35
 * @Version 1.0
 */
@Slf4j
@Component
public class QuartzTaskUtil {
    @Resource
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param cron             时间设置
     */
    public void addJob(String jobName, String jobGroupName,
                       String triggerName, String triggerGroupName, Class jobClass, String cron)
            throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        // 用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroupName).build();

        // 检查任务是否已存在
        if (scheduler.checkExists(JobKey.jobKey(jobName, jobGroupName))) {
            log.info("任务已存在: {}, {}", jobName, jobGroupName);
            return;
        }

        // 构建一个触发器，规定触发的规则
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param cron             时间设置
     */
    public void updateScheduleJob(String triggerName, String triggerGroupName, String cron) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        //获取到对应任务的触发器
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
        //设置定时任务执行方式
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        //重新构建任务的触发器trigger
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        //重置对应的job
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 移除任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     */
    public void removeJob(String jobName, String jobGroupName)
            throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = new JobKey(jobName, jobGroupName);
        scheduler.deleteJob(jobKey);
    }

    /**
     * 暂停任务
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     */
    public void pauseJob(String jobName, String jobGroupName) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
    }

    /**
     * 恢复任务
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     */
    public void resumeJob(String jobName, String jobGroupName) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
    }

    /**
     * 启动所有任务
     */
    public void startJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
    }

    /**
     * 关闭所有定时任务
     */
    public void shutdownJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    static class T {
        Integer i, j;
    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        List<T> longs = Arrays.asList(new T(), new T());
        List<T> collect = longs.stream().filter(t -> t.i != null)
                .peek(t -> {
                    t.i = 0;
                    t.j = 0;
                })
                .collect(Collectors.toList());
        System.out.println(collect);
    }
}
