package com.qring.common.test.lock;

import com.qring.common.test.Application;
import com.qring.common.test.service.FaulteventService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/10/21 17:08
 * @Version 1.0
 */
@Slf4j
@SpringBootTest(classes = Application.class)
public class LockTest {
    @Resource
    private FaulteventService faulteventService;

    @Test
    public void test() {
        faulteventService.test();
    }
}
