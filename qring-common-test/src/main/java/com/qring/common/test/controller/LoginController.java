package com.qring.common.test.controller;

import com.qring.common.base.result.ResultDTO;
import com.qring.common.test.common.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

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
    public ResultDTO<List<User>> login() {
        User user = new User();
        user.setName("1");
        user.setStatus("0");
        User user1 = new User();
        user1.setName("1");
        user1.setStatus("0");
        return ResultDTO.success(Arrays.asList(user1, user));
    }


    static class A {
        boolean a;
        int b;
        boolean c;
    }

//    public static void main(String[] args) {
//        System.out.println(ClassLayout.parseInstance(new A()).toPrintable());
//    }
}
