package com.qring.common.test.quartz.util;

import com.qring.common.test.quartz.model.CronScheduleModel;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;

/**
 * @Author Qring
 * @Description 生成 cron 表达式工具
 * @Date 2022/9/30 15:58
 * @Version 1.0
 */
public class CronUtil {


    /**
     * 构建Cron表达式
     * convert Date to cron, eg:  "0 06 10 15 1 ? 2014"
     *
     * @param date : 时间点
     * @return String
     */
    public static String getCron(LocalDateTime date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return cn.hutool.core.date.DateUtil.format(date, dateFormat);
    }


    /**
     * 构建Cron表达式
     *
     * @param model cron生成参数
     * @return String
     */
    public static String getCron(CronScheduleModel model) {
        if (null == model.getJobType()) {
            throw new IllegalArgumentException("cron生成需要指定，生成周期（每月，每周，每月）");
        }
        StringBuilder cronExp = new StringBuilder();
        //秒
        cronExp.append(model.getSecond() == null ? 0 : model.getSecond()).append(" ");
        //分
        cronExp.append(model.getMinuter() == null ? 0 : model.getMinuter()).append(" ");
        //小时
        cronExp.append(model.getHour() == null ? 0 : model.getHour()).append(" ");
        switch (model.getJobType()) {
            case JOB_TYPE_DAY:
                //每天
                //日
                cronExp.append("* ");
                //月
                cronExp.append("* ");
                //周
                cronExp.append("?");
                break;
            case JOB_TYPE_WEEK:
                //按每周
                if (CollectionUtils.isEmpty(model.getDayOfWeekList())) {
                    throw new IllegalArgumentException("cron 按周执行，需要指定一周的哪些天");
                }
                cronExp.append("? ");
                //月份
                cronExp.append("* ");
                //周
                for (int index = 0; index < model.getDayOfWeekList().size(); index++) {
                    if (index == 0) {
                        cronExp.append(model.getDayOfWeekList().get(index));
                    } else {
                        cronExp.append(",").append(model.getDayOfWeekList().get(index));
                    }
                }
                break;
            case JOB_TYPE_MONTH:
                //按每月
                if (CollectionUtils.isEmpty(model.getDayOfMonthList())) {
                    throw new IllegalArgumentException("cron 按月执行，需要指定月的哪些天");
                }
                //一个月中的哪几天
                for (int index = 0; index < model.getDayOfMonthList().size(); index++) {
                    if (index == 0) {
                        cronExp.append(model.getDayOfMonthList().get(index));
                    } else {
                        cronExp.append(",").append(model.getDayOfMonthList().get(index));
                    }
                }
                //月份
                cronExp.append(" * ");
                //周
                cronExp.append("?");
                break;
            default:
                throw new IllegalArgumentException("cron生成不支持的生成周期");
        }
        return cronExp.toString();
    }
}
