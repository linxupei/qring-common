package com.qring.common.test.service;

import java.time.LocalDateTime;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/14 15:36
 * @Version 1.0
 */
public interface ITimeService {

    //@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    void selectAndInsert(LocalDateTime eventTime);
}
