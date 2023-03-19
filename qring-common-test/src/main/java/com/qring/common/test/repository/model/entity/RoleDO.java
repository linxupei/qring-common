package com.qring.common.test.repository.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/3/19 19:21
 * @Version 1.0
 */
@Data
@TableName(value = "qr_role")
public class RoleDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "role_name")
    private String roleName;
}