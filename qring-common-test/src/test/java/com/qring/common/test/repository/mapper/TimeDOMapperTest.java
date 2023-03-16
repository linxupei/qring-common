package com.qring.common.test.repository.mapper;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qring.common.test.QringCommonTestApplication;
import com.qring.common.test.repository.model.entity.FaulteventDO;
import com.qring.common.test.service.FaulteventService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        for (int i = 0; i < 0; i++) {
            FaulteventDO faulteventDO = new FaulteventDO();
            faulteventDO.setTenantPkId(i + "1555428");
            faulteventDO.setTenantPhone(i + "13824128322");
            faulteventDO.setTenantName(i + "hwm");
            faulteventDO.setTenantCode(i + "ZH1000");
            faulteventDO.setRecoverTime(LocalDateTime.now());
            faulteventDO.setProjectPkId(i + "1554668581481566210");
            faulteventDO.setModifyUserPkId(i + "root");
            faulteventDO.setModifyTime(LocalDateTime.now());
            faulteventDO.setMeterSn(i + "1555428127333511169");
            faulteventDO.setMeterPkId(i + "1555428127333511169");
            faulteventDO.setMeterName(i + "1555428127333511169");
            faulteventDO.setEventType(i + "1555428127333511169");
            faulteventDO.setEventTime(LocalDateTime.now());

            faulteventDO.setEventDetail(i + "1555428127333511169");
            faulteventDO.setEventCode(i + "1555428127333511169");
            faulteventDO.setCreateUserPkId(i + "1555428127333511169");
            faulteventDO.setCreateTime(LocalDateTime.now());
            faulteventDO.setConfirmTime(LocalDateTime.now());
            faulteventDO.setConfirmFlag(1);
            faulteventDO.setConfirmDesc(i + "1555428127333511169");
            faulteventDOList.add(faulteventDO);
        }
    }

    private static List<FaulteventDO> getFaulteventDOList() {
        return null;
    }

    public static void main(String[] args) {
        List<FaulteventDO> list = CollectionUtil.defaultIfEmpty(getFaulteventDOList(), Collections.emptyList());
        list.sort(Comparator.comparing(FaulteventDO::getConfirmFlag));

    }

    @Test
    public void testMapper() {
        faulteventMapper.selectList(new LambdaQueryWrapper<FaulteventDO>().eq(FaulteventDO::getConfirmDesc, ""));
        for (int j = 0; j < 10; j++) {
            List<FaulteventDO> faulteventDOList1 = new ArrayList<>(100000);
            for (int i = 0; i < 100000; i++) {
                FaulteventDO faulteventDO = new FaulteventDO();
                faulteventDO.setTenantPkId(i + "1555428");
                faulteventDO.setTenantPhone(i + "13822");
                faulteventDO.setTenantName(i + "hwm");
                faulteventDO.setTenantCode(i + "ZH1000");
                faulteventDO.setRecoverTime(LocalDateTime.now());
                faulteventDO.setProjectPkId(String.valueOf(i));
                faulteventDO.setModifyUserPkId(i + "root");
                faulteventDO.setModifyTime(LocalDateTime.now());
                faulteventDO.setMeterSn(i + "155");
                faulteventDO.setMeterPkId(i + "15554");
                faulteventDO.setMeterName(i + "155");
                faulteventDO.setEventType(i + "155569");
                faulteventDO.setEventTime(LocalDateTime.now());

                faulteventDO.setEventDetail(i + "1555169");
                faulteventDO.setEventCode(i + "155169");
                faulteventDO.setCreateUserPkId(i + "155511");
                faulteventDO.setCreateTime(LocalDateTime.now());
                faulteventDO.setConfirmTime(LocalDateTime.now());
                faulteventDO.setConfirmFlag(1);
                faulteventDO.setConfirmDesc(i + "1555");
                faulteventDOList1.add(faulteventDO);
            }
            faulteventService.batchInsert(faulteventDOList1);
        }

//        for (int i = 0; i < faulteventListList.size(); i++) {
//            faulteventMapper.batchInsert(faulteventListList.get(i));
//        }
    }

    @Test
    public void testService() {
        List<FaulteventDO> list = faulteventMapper.selectList(new LambdaQueryWrapper<FaulteventDO>()
                .le(FaulteventDO::getEventPkId, 15584292));
        System.out.println(list);
    }

}