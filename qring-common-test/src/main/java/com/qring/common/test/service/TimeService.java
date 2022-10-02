package com.qring.common.test.service;

import com.qring.common.test.repository.mapper.TimeMapper;
import com.qring.common.test.repository.model.entity.TimeDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/14 14:05
 * @Version 1.0
 */
@Service
@Slf4j
public class TimeService implements ITimeService {
    @Resource
    private TimeMapper timeMapper;

    public static void main(String[] args) {
        String format = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println(LocalDateTime.parse(format));
    }

    @Override
    public void selectAndInsert(LocalDateTime eventTime) {
//        Time time = timeMapper.selectOne(new LambdaQueryWrapper<Time>()
//                .between(Time::getEventTime, eventTime.minusHours(24), eventTime)
//                .last("limit 1")
//        );
//        log.info("thread: {}, time: {}, timestamp: {}", Thread.currentThread(), JSONUtil.toJsonStr(time), LocalDateTime.now());
//        if (time == null) {
            TimeDO t = new TimeDO();
            t.setEventTime(eventTime);
            t.setCreateTime(eventTime);

            timeMapper.insertOnWhere(t, eventTime);
        //}

    }
}
