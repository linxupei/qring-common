package com.qring.common.test.quartz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Qring
 * @Description Cron 表达式
 * @Date 2022/9/30 16:00
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CronScheduleModel {

    /**
     * cron周期
     */
    private CronJobTypeEnum jobType;
    
    /**
     * 小时，未指定则为0
     */
    private Integer hour;

    /**
     * 分钟，未指定则为0
     */
    private Integer minuter;

    /**
     * 秒，未指定则为0
     */
    private Integer second;

    /**
     * 周的哪几天, jobType == ECronJobType.JOB_TYPE_WEEK 的时候使用
     */
    private List<Integer> dayOfWeekList;

    /**
     * 月的哪几天, jobType == ECronJobType.JOB_TYPE_MONTH 的时候使用
     */
    private List<Integer> dayOfMonthList;
}
