package com.qring.common.test.annotation;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/12/14 15:59
 * @Version 1.0
 */
@Foo(value = "2", name = "FooTwo", cron = "twoCron")
public class FooTwo extends FooAbstract {

    @Override
    public String jobName() {
        return super.jobName();
    }

    @Override
    public String jobTriggerCron() {
        return super.jobTriggerCron();
    }
}
