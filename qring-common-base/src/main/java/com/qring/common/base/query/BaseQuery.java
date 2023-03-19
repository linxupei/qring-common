package com.qring.common.base.query;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Qring
 * @Description 基础请求
 * @Date 2023/3/18 22:56
 * @Version 1.0
 */
@Data
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
}
