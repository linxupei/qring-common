package com.qring.common.test.repository.mapper;


import cn.hutool.core.bean.BeanUtil;
import com.qring.common.test.Application;
import com.qring.common.test.repository.model.Faultevent;
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
@SpringBootTest(classes = Application.class)
class TimeMapperTest {
    @Resource
    private FaulteventMapper faulteventMapper;
    @Resource
    private FaulteventService faulteventService;

    private static List<Faultevent> faulteventList;

    private static List<List<Faultevent>> faulteventListList;

    // INSERT INTO `p_sys_faultevent` VALUES
    // ('1573234095176773633', '1554668581481566210', 'GJ2022092300000009', '1003', '2022-09-23 16:53:31', '2022-09-23 16:53:32',
    // '测试告警', '1555428127333511169', 'ZH2022080500002', 'hwm', '13824128322', NULL, NULL, NULL, 0, NULL, NULL, '2022-09-23 16:53:32',
    // NULL, '2022-09-23 16:53:32', NULL);

    static {
        faulteventListList = new ArrayList<>();
        faulteventList = new ArrayList<>();
        List<Faultevent> t = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Faultevent faultevent = new Faultevent();
            faultevent.setTenantPkId("1555428127333511169");
            faultevent.setTenantPhone("13824128322");
            faultevent.setTenantName("hwm");
            faultevent.setTenantCode("ZH1000");
            faultevent.setRecoverTime(new Date());
            faultevent.setProjectPkId("1554668581481566210");
            faultevent.setModifyUserPkId("root");
            faultevent.setModifyTime(new Date());
            faultevent.setMeterSn("1555428127333511169");
            faultevent.setMeterPkId("1555428127333511169");
            faultevent.setMeterName("1555428127333511169");
            faultevent.setEventType("1555428127333511169");
            faultevent.setEventTime(new Date());

            faultevent.setEventDetail("1555428127333511169");
            faultevent.setEventCode("1555428127333511169");
            faultevent.setCreateUserPkId("1555428127333511169");
            faultevent.setCreateTime(new Date());
            faultevent.setConfirmTime(new Date());
            faultevent.setConfirmFlag((byte) 1);
            faultevent.setConfirmDesc("1555428127333511169");
            faulteventList.add(faultevent);
            t.add(BeanUtil.copyProperties(faultevent, Faultevent.class));
            if (t.size() == 1000) {
                faulteventListList.add(t);
                t = new ArrayList<>();
            }
        }
    }

    @Test
    public void testMapper() {
        long stime = System.currentTimeMillis(); // 统计开始时间
        for (int i = 0; i < faulteventListList.size(); i++) {
            faulteventMapper.batchInsert(faulteventListList.get(i));
        }
        long etime = System.currentTimeMillis(); // 统计结束时间
        System.out.println("testMapper执行时间：" + (etime - stime));
    }

    @Test
    public void testService() {
        long stime = System.currentTimeMillis(); // 统计开始时间
        faulteventService.saveBatch(faulteventList);
        long etime = System.currentTimeMillis(); // 统计结束时间
        System.out.println("testService执行时间：" + (etime - stime));
    }
}