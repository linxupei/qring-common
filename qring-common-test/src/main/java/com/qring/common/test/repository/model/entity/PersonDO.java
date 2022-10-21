package com.qring.common.test.repository.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* @Author Qring
* @Description TODO
* @Date 2022/9/30 10:55
* @Version 1.0
*/
@Data
@TableName(value = "person")
public class PersonDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "age")
    private Integer age;

    @TableField(value = "score")
    private Integer score;
}