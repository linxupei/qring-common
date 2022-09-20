package com.qring.common.test.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qring.common.test.repository.model.pojo.Time;
import com.qring.common.test.repository.model.vo.TimeVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/14 19:18
 * @Version 1.0
 */

public interface TimeMapper extends BaseMapper<Time> {
    int insertOrUpdate(Time record);

    int insertOrUpdateSelective(Time record);

    List<TimeVO> list();

    int insertOnWhere(@Param("time") Time time, @Param("eventTime") LocalDateTime eventTime);
}