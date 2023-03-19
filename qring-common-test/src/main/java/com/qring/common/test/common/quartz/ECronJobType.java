package com.qring.common.test.common.quartz;

/**
 * @Author Qring
 * @Description cron 执行周期
 * @Date 2022/10/8 9:48
 * @Version 1.0
 */
public enum ECronJobType {
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

    ECronJobType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
