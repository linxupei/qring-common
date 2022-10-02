package com.qring.common.test.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qring.common.test.repository.model.entity.FaulteventDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Author Qring
* @Description TODO
* @Date 2022/9/27 10:27
* @Version 1.0
*/

public interface FaulteventMapper extends BaseMapper<FaulteventDO> {
    int batchInsert(@Param("list") List<FaulteventDO> list);
}