package com.qring.swagger.test.controller;

import com.qring.swagger.test.repository.model.dto.ResponseDTO;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/2 9:57
 * @Version 1.0
 */
@RestController
public class TestController {

    @PostMapping("test")
    public void test(@RequestBody ResponseDTO responseDto) {
        System.out.println(responseDto);
        //return responseDto;
    }
}
