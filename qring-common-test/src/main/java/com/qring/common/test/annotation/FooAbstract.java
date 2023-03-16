package com.qring.common.test.annotation;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/12/14 15:56
 * @Version 1.0
 */
public abstract class FooAbstract {

    private Environment environment;
    @Value("")
    private String jobName;
    private String jobCron;


    /**
     * 任务名称
     *
     * @return 任务名称
     */
    public String jobName() {
        Foo annotation = AnnotationUtils.findAnnotation(this.getClass(), Foo.class);
        if (ObjectUtil.isEmpty(annotation) || ObjectUtil.isEmpty(annotation.name())) {
            return "";
        }
        return annotation.value();
    }

    /**
     * 任务触发cron表达式
     *
     * @return 任务触发cron表达式
     */
    public String jobTriggerCron() {
        Foo annotation = AnnotationUtils.findAnnotation(this.getClass(), Foo.class);
        if (ObjectUtil.isEmpty(annotation) || ObjectUtil.isEmpty(annotation.cron())) {
            return "";
        }
        return annotation.cron();
    }
}
