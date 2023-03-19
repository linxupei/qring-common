package com.qring.common.test.service.security;

import cn.hutool.core.lang.Assert;
import com.qring.common.test.repository.UserRepository;
import com.qring.common.test.repository.mapper.UserMapper;
import com.qring.common.test.repository.model.entity.UserDetailDO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/3/19 20:03
 * @Version 1.0
 */
@Transactional
@Service
public class UserDetailsPasswordServiceImpl implements UserDetailsPasswordService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        UserDetailDO userDetailDO = userRepository.getUserDetailDO(user.getUsername());
        Assert.notNull(userDetailDO, "user not exist!");
        userDetailDO.getUser().setPassword(newPassword);
        userMapper.updateById(userDetailDO.getUser());
        return userDetailDO;
    }
}
