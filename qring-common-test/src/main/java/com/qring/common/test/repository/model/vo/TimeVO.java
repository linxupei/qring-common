package com.qring.common.test.repository.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
* @Author Qring
* @Description TODO
* @Date 2022/9/9 13:38
* @Version 1.0
*/

@Data
public class TimeVO {
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime eventTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createTime;
}