package com.qring.common.test.repository;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qring.common.test.repository.mapper.RoleMapper;
import com.qring.common.test.repository.mapper.UserMapper;
import com.qring.common.test.repository.model.entity.RoleDO;
import com.qring.common.test.repository.model.entity.UserDO;
import com.qring.common.test.repository.model.entity.UserDetailDO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/3/19 20:09
 * @Version 1.0
 */
@Repository
public class UserRepository {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    public UserDetailDO getUserDetailDO(String username) {
        UserDO userDO = userMapper.selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getUsername, username));
        if (ObjectUtil.isNull(userDO)) {
            return null;
        }
        List<RoleDO> roles = roleMapper.getRoleByUserId(userDO.getId());
        return new UserDetailDO(userDO, roles);
    }
}
