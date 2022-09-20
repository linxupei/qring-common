package com.qring.common.test.repository.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qring.common.test.repository.model.pojo.Customers;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/8/25 15:10
 * @Version 1.0
 */
class CustomersMapperTest {
    public static void main(String[] args) {
        LambdaQueryWrapper<Customers> queryWrapper = new LambdaQueryWrapper<Customers>()
                .eq(Customers::getCustCity, "")
                .orderByAsc(Customers::getCustCity);
    }
}