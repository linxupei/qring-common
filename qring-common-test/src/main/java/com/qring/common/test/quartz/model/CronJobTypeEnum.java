package com.qring.common.test.quartz.model;

/**
 * @Author Qring
 * @Description 定时任务运行周期
 * @Date 2022/9/30 16:02
 * @Version 1.0
 */
public enum CronJobTypeEnum {
    /**
     * 每天
     */
    JOB_TYPE_DAY(1),
    /**
     * 每周
     */
    JOB_TYPE_WEEK(2),
    /**
     * 每月
     */
    JOB_TYPE_MONTH(3);

    private final int type;

    CronJobTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
