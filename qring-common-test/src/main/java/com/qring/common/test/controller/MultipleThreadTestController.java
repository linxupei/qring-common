package com.qring.common.test.controller;

import com.qring.common.base.exception.BizException;
import com.qring.common.base.query.PageQuery;
import com.qring.common.base.result.CommonResultCode;
import com.qring.common.test.common.quartz.QuartzTaskUtil;
import com.qring.common.test.service.MultipleThreadService;
import org.quartz.SchedulerException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public void test1(@Validated @RequestBody PageQuery query) {
        throw new BizException(CommonResultCode.ERROR_PARAM);
    }

    @GetMapping("test2")
    public void test2() {
        throw new BizException(CommonResultCode.BUSINESS_ERROR);
    }

    @GetMapping("test3")
    public void test3() throws SchedulerException {
    }
}
