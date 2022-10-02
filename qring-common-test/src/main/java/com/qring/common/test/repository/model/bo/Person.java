package com.qring.common.test.repository.model.bo;

import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/9/29 15:00
 * @Version 1.0
 */
public record Person(String name, Integer age, List<String> tag) {
}
