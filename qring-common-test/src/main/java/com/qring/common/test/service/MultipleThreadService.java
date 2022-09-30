package com.qring.common.test.service;

import com.qring.common.test.config.AtomicConcurrentTransactionalExecutor;
import com.qring.common.test.repository.mapper.PersonMapper;
import com.qring.common.test.repository.model.PersonDO;
import com.zaxxer.hikari.util.UtilityElf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/30 10:45
 * @Version 1.0
 */
@Service
public class MultipleThreadService {

    ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5), new UtilityElf.DefaultThreadFactory("test", false));

    @Resource
    private PersonMapper personMapper;

    @Resource
    AtomicConcurrentTransactionalExecutor executor;

    @Transactional(rollbackFor = Exception.class)
    public void test() {
        Runnable test1 = () -> {
            PersonDO personDO13 = new PersonDO();
            personDO13.setName("123b");
            personMapper.insert(personDO13);
            throw new RuntimeException("test1");
        };
        Runnable test2 = () -> {
            PersonDO personDO1 = new PersonDO();
            personDO1.setName("123a");
            personMapper.insert(personDO1);
        };
        Runnable test3 = () -> {
            PersonDO personDO12 = new PersonDO();
            personDO12.setName("123c");
            personMapper.insert(personDO12);
        };

        executor.execute(executorService, test1, test2, test3);
    }
}
