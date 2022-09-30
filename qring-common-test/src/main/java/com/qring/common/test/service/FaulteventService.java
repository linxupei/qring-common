package com.qring.common.test.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qring.common.test.repository.mapper.FaulteventMapper;
import com.qring.common.test.repository.mapper.PersonMapper;
import com.qring.common.test.repository.model.PersonDO;
import com.qring.common.test.repository.model.entity.FaulteventDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
* @Author Qring
* @Description TODO
* @Date 2022/9/27 10:27
* @Version 1.0
*/

@Service
public class FaulteventService extends ServiceImpl<FaulteventMapper, FaulteventDO> {
    @Resource
    private PersonMapper personMapper;
    
    public int batchInsert(List<FaulteventDO> list) {
        return baseMapper.batchInsert(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public void test() {
        PersonDO personDO = new PersonDO();
        personDO.setName("123d");
        personMapper.insert(personDO);
        throw new RuntimeException("test3");
    }
}
