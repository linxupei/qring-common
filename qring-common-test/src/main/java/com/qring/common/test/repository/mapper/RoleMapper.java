package com.qring.common.test.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qring.common.test.repository.model.entity.RoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/3/19 19:21
 * @Version 1.0
 */
public interface RoleMapper extends BaseMapper<RoleDO> {

    List<RoleDO> getRoleByUserId(@Param("userId") Long userId);
}