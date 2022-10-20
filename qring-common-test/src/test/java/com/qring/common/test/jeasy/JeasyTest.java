package com.qring.common.test.jeasy;

import com.qring.common.test.repository.model.entity.TimeDO;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

/**
 * @Author Qring
 * @Description Jeasy框架测试
 * @Date 2022/10/13 14:31
 * @Version 1.0
 */
@Slf4j
public class JeasyTest {

    private EasyRandom easyRandom;
    private int i;
    @BeforeEach
    public void init() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .seed(123L)
                .objectPoolSize(100)
                .randomizationDepth(3)
                .charset(StandardCharsets.UTF_8)
                .dateRange(LocalDate.now(), LocalDate.now().plusDays(1))
                .timeRange(LocalTime.now(), LocalTime.now().plusHours(1))
                .stringLengthRange(5, 50)
                .collectionSizeRange(1, 10)
                .scanClasspathForConcreteTypes(true)
                .overrideDefaultInitialization(false)
                .ignoreRandomizationErrors(true)
                ;
        easyRandom = new EasyRandom(parameters);
    }

    @Test
    public void test() {
        TimeDO timeDO = easyRandom.nextObject(TimeDO.class);
        System.out.println(timeDO);
        testException(new Random().nextInt());
    }

    public void testException(int i) {
        boolean b = new Random().nextBoolean();
        if (b) {
            throw new IllegalArgumentException("@@@");
        }
    }
}
