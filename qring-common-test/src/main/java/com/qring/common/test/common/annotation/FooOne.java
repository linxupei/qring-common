package com.qring.common.test.common.annotation;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/12/14 15:59
 * @Version 1.0
 */
@Foo(value = "${nacos.config.server-addr:127.0.0.1}", name = "${nacos.config.server-addr:127.0.0.1}", cron = "oneCron")
public class FooOne extends FooAbstract {

    @Override
    public String jobName() {
        return super.jobName();
    }

    @Override
    public String jobTriggerCron() {
        return super.jobTriggerCron();
    }
}
