package com.qring.common.test.repository.mapper;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qring.common.test.QringCommonTestApplication;
import com.qring.common.test.repository.model.entity.FaulteventDO;
import com.qring.common.test.service.FaulteventService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/9 13:39
 * @Version 1.0
 */
@Slf4j
@SpringBootTest(classes = QringCommonTestApplication.class)
class TimeDOMapperTest {
    @Resource
    private FaulteventMapper faulteventMapper;
    @Resource
    private FaulteventService faulteventService;

    private static List<FaulteventDO> faulteventDOList;

    private static List<List<FaulteventDO>> faulteventListList;

    // INSERT INTO `p_sys_faultevent` VALUES
    // ('1573234095176773633', '1554668581481566210', 'GJ2022092300000009', '1003', '2022-09-23 16:53:31', '2022-09-23 16:53:32',
    // '测试告警', '1555428127333511169', 'ZH2022080500002', 'hwm', '13824128322', NULL, NULL, NULL, 0, NULL, NULL, '2022-09-23 16:53:32',
    // NULL, '2022-09-23 16:53:32', NULL);

    static {
        faulteventListList = new ArrayList<>();
        faulteventDOList = new ArrayList<>();
        List<FaulteventDO> t = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            FaulteventDO faulteventDO = new FaulteventDO();
            faulteventDO.setTenantPkId("1555428127333511169");
            faulteventDO.setTenantPhone("13824128322");
            faulteventDO.setTenantName("hwm");
            faulteventDO.setTenantCode("ZH1000");
            faulteventDO.setRecoverTime(new Date());
            faulteventDO.setProjectPkId("1554668581481566210");
            faulteventDO.setModifyUserPkId("root");
            faulteventDO.setModifyTime(new Date());
            faulteventDO.setMeterSn("1555428127333511169");
            faulteventDO.setMeterPkId("1555428127333511169");
            faulteventDO.setMeterName("1555428127333511169");
            faulteventDO.setEventType("1555428127333511169");
            faulteventDO.setEventTime(new Date());

            faulteventDO.setEventDetail("1555428127333511169");
            faulteventDO.setEventCode("1555428127333511169");
            faulteventDO.setCreateUserPkId("1555428127333511169");
            faulteventDO.setCreateTime(new Date());
            faulteventDO.setConfirmTime(new Date());
            faulteventDO.setConfirmFlag((byte) 1);
            faulteventDO.setConfirmDesc("1555428127333511169");
            faulteventDOList.add(faulteventDO);
            t.add(BeanUtil.copyProperties(faulteventDO, FaulteventDO.class));
            if (t.size() == 1000) {
                faulteventListList.add(t);
                t = new ArrayList<>();
            }
        }
    }

    @Test
    public void testMapper() {
        faulteventMapper.selectList(new LambdaQueryWrapper<FaulteventDO>().eq(FaulteventDO::getConfirmDesc, ""));
        for (int i = 0; i < faulteventListList.size(); i++) {
            faulteventMapper.batchInsert(faulteventListList.get(i));
        }
    }

    @Test
    public void testService() {
        faulteventService.saveBatch(faulteventDOList);
    }

}