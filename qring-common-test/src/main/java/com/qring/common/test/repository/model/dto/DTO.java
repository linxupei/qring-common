package com.qring.common.test.repository.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2022/8/31 10:10
 * @Version 1.0
 */
@Data
@ApiOperation(value = "Create user",
        notes = "This method creates a new user")
public class DTO {

    @JsonProperty
    private String str;

    private List<String> list;
}
