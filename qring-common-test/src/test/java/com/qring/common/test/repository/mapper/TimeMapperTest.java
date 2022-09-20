package com.qring.common.test.repository.mapper;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.qring.common.test.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/9 13:39
 * @Version 1.0
 */
@Slf4j
@SpringBootTest(classes = Application.class)
class TimeMapperTest {
    @Resource
    private TimeMapper timeMapper;

    @Test
    public void test() {
        Date date = DateUtil.parse("2022-09-08 06:30:30.999999");
        System.out.println(LocalDateTimeUtil.of(date));
        System.out.println(LocalDateTimeUtil.of(date).withNano(0));

    }
}