package com.qring.common.test.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qring.common.test.repository.mapper.FaulteventMapper;
import com.qring.common.test.repository.model.Faultevent;
import org.springframework.stereotype.Service;

import java.util.List;
/**
* @Author Qring
* @Description TODO
* @Date 2022/9/27 10:27
* @Version 1.0
*/

@Service
public class FaulteventService extends ServiceImpl<FaulteventMapper, Faultevent> {

    
    public int batchInsert(List<Faultevent> list) {
        return baseMapper.batchInsert(list);
    }
}
