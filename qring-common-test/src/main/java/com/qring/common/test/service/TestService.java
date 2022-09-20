package com.qring.common.test.service;

import com.qring.common.test.repository.model.vo.TimeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/14 14:03
 * @Version 1.0
 */
@Service
public class TestService {
    @Resource
    private TimeService timeService;

    @Transactional(rollbackFor = Exception.class)
    public TimeVO test() {
        LocalDateTime time = LocalDateTime.of(2011, 1, 1, 1, 1, 1, 0);
        timeService.selectAndInsert(time);

        try {
            TimeUnit.SECONDS.sleep(5);
            //throw new RuntimeException("exception");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
