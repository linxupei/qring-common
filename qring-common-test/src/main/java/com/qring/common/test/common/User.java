package com.qring.common.test.common;

import com.qring.common.test.common.config.Dict;
import lombok.Data;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/7/10 21:11
 * @Version 1.0
 */
@Data
public class User {
    @Dict(defaultValue = "name")
    private String name;
    @Dict(defaultValue = "status")
    private String status;
}
