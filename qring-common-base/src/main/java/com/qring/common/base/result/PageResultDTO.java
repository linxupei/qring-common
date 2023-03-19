package com.qring.common.base.result;

import com.qring.common.base.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author Qring
 * @Description 分页统一结果返回
 * @Date 2023/3/18 22:52
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageResultDTO<T> extends ResultDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageSize;

    private Integer pageNo;

    private Long totalCount;

    public PageResultDTO() {
        setCode(CommonResultCode.SUCCESS.getCode());
    }

    public PageResultDTO(PageQuery pageQuery) {
        if (pageQuery != null) {
            this.pageNo = pageQuery.getPageNo();
            this.pageSize = pageQuery.getPageSize();
        }
        setCode(CommonResultCode.SUCCESS.getCode());
    }

    public static <T> PageResultDTO<T> success(T value) {
        PageResultDTO<T> r = new PageResultDTO<>();
        r.setData(value);
        return r;
    }

    public static <T> PageResultDTO<T> fail(ResultCode resultCode) {
        PageResultDTO<T> r = new PageResultDTO<>();
        r.setCode(resultCode.getCode());
        r.setMsg(resultCode.getMsg());
        return r;
    }

    public static <T> PageResultDTO<T> fail() {
        PageResultDTO<T> r = new PageResultDTO<>();
        r.setCode(CommonResultCode.SYSTEM_ERROR.getCode());
        r.setMsg(CommonResultCode.SYSTEM_ERROR.getMsg());
        return r;
    }

    public static <T> PageResultDTO<T> fail(String msg) {
        PageResultDTO<T> r = new PageResultDTO<>();
        r.setCode(CommonResultCode.SYSTEM_ERROR.getCode());
        r.setMsg(msg);
        return r;
    }
}
