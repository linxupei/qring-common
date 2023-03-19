package com.qring.common.base.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author Qring
 * @Description 分页请求
 * @Date 2023/3/18 22:57
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "common.pageSize.not.null")
    @ApiModelProperty(value = "每页数量(不能为空)", example = "10")
    @Range(min = 1, max = 200L, message = "common.pageSize.error")
    private Integer pageSize;

    @NotNull(message = "common.pageNo.not.null")
    @ApiModelProperty(value = "页码(不能为空)", example = "1")
    @Min(value = 1, message = "common.pageNo.error")
    private Integer pageNo;
}
