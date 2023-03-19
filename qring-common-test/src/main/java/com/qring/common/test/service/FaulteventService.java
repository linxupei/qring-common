package com.qring.common.test.service;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qring.common.test.repository.mapper.FaulteventMapper;
import com.qring.common.test.repository.mapper.PersonMapper;
import com.qring.common.test.repository.model.entity.FaulteventDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/27 10:27
 * @Version 1.0
 */
@Slf4j
@Service
public class FaulteventService extends ServiceImpl<FaulteventMapper, FaulteventDO> {
    @Resource
    private PersonMapper personMapper;
    @Resource
    private PlatformTransactionManager manager;

    @Resource
    private TransactionDefinition transactionDefinition;

    public int batchInsert(List<FaulteventDO> list) {
        return baseMapper.batchInsert(list);
    }

    public void test() {
        TransactionStatus status = manager.getTransaction(transactionDefinition);
        try {
            log.info(JSONUtil.toJsonStr(baseMapper.selectById(1)));
            manager.commit(status);
        } catch (Exception e) {
            manager.rollback(status);
        } finally {
            FaulteventDO faulteventDO = new FaulteventDO();
            faulteventDO.setTenantPkId("1555428127333511169");
            faulteventDO.setTenantPhone("13824128322");
            faulteventDO.setTenantName("hwm");
            faulteventDO.setTenantCode("ZH1000");
            faulteventDO.setRecoverTime(LocalDateTime.now());
            faulteventDO.setProjectPkId("1554668581481566210");
            faulteventDO.setModifyUserPkId("root");
            faulteventDO.setModifyTime(LocalDateTime.now());
            faulteventDO.setMeterSn("1555428127333511169");
            faulteventDO.setMeterPkId("1555428127333511169");
            faulteventDO.setMeterName("1555428127333511169");
            faulteventDO.setEventType("1555428127333511169");
            faulteventDO.setEventTime(LocalDateTime.now());

            faulteventDO.setEventDetail("1555428127333511169");
            faulteventDO.setEventCode("1555428127333511169");
            faulteventDO.setCreateUserPkId("1555428127333511169");
            faulteventDO.setCreateTime(LocalDateTime.now());
            faulteventDO.setConfirmTime(LocalDateTime.now());
            faulteventDO.setConfirmFlag(1);
            faulteventDO.setConfirmDesc("1555428127333511169");
            baseMapper.insert(faulteventDO);
            log.info(JSONUtil.toJsonStr(faulteventDO));
        }
    }
}
