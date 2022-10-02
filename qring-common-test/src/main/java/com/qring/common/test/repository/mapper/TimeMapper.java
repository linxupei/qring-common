package com.qring.common.test.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qring.common.test.repository.model.entity.TimeDO;
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

public interface TimeMapper extends BaseMapper<TimeDO> {
    int insertOrUpdate(TimeDO record);

    int insertOrUpdateSelective(TimeDO record);

    List<TimeVO> list();

    int insertOnWhere(@Param("timeDO") TimeDO timeDO, @Param("eventTime") LocalDateTime eventTime);
}