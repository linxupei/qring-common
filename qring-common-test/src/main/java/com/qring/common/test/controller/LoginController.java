package com.qring.common.test.controller;

import com.qring.common.base.result.ResultDTO;
import com.qring.common.test.common.util.SecurityUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/3/19 21:50
 * @Version 1.0
 */
@RestController
public class LoginController {

    @Resource
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "login")
    public ResultDTO<Object> login(@RequestBody Map<String, String> params) {
        Object userInfo = SecurityUtil.login(params.get("username"), params.get("password"), authenticationManager);
        return ResultDTO.success(userInfo);
    }
}
