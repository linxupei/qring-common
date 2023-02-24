package com.qring.common.test.controller;

import com.qring.common.test.quartz.HelloJob;
import com.qring.common.test.quartz.QuartzTaskUtil;
import com.qring.common.test.service.MultipleThreadService;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/30 11:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/multiple/thread")
public class MultipleThreadTestController {
    @Resource
    private MultipleThreadService multipleThreadService;
    @Resource
    private QuartzTaskUtil quartzTaskUtil;

    @GetMapping("test1")
    public void test1() {
        //multipleThreadService.test();
    }

    @GetMapping("test2")
    public void test2() throws SchedulerException {
        quartzTaskUtil.addJob("1", "1", "1", "1",
                HelloJob.class, "");
    }
}
