package com.qring.common.base.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Consumer;

/**
 * @Author Qring
 * @Description 批量操作工具
 * @Date 2023/2/24 20:26
 * @Version 1.0
 */
public class BatchUtil {

    private static final int DEFAULT_SIZE = 1000;

    private BatchUtil(){}

    public static <T> void batch(List<T> data, Consumer<List<T>> process) {
        batch(DEFAULT_SIZE, data, process);
    }

    public static <T> void batch(int batchSize, List<T> data, Consumer<List<T>> process) {
        if (ObjectUtil.isEmpty(data)) {
            return;
        }
        Assert.isTrue(batchSize > 0, "batch size must be greater than zero");
        Lists.partition(data, batchSize).forEach(process);
    }
}
