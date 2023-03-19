package com.qring.common.test.common.quartz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/8 9:47
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
    private ECronJobType jobType;
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
