package com.qring.common.test.repository.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
* @Author Qring
* @Description TODO
* @Date 2022/9/9 13:38
* @Version 1.0
*/
@Data
@TableName(value = "`time`")
public class TimeDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "event_time")
    private LocalDateTime eventTime;

    @TableField(value = "create_time")
    private LocalDateTime createTime;
}