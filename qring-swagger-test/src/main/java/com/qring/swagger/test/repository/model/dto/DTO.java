package com.qring.swagger.test.repository.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.qring.swagger.test.jaskson.StringToListWithCommaDeserializer;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(dataType = "java.lang.String")
//    @ApiModelProperty(value = "str")
    private String str;

    @JsonDeserialize(using = StringToListWithCommaDeserializer.class)
    //@ApiModelProperty(dataType = "java.lang.String")
//    @ApiModelProperty(value = "list")
    private List<String> list;
}