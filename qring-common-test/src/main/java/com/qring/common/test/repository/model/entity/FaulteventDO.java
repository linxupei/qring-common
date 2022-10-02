package com.qring.common.test.repository.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
* @Author Qring
* @Description TODO
* @Date 2022/9/27 10:27
* @Version 1.0
*/
@Data
@TableName(value = "faultevent")
public class FaulteventDO {
    /**
     * 事件PkID
     */
    @TableId(value = "eventPkId", type = IdType.AUTO)
    private Long eventPkId;

    /**
     * 项目PkID
     */
    @TableField(value = "projectPkId")
    private String projectPkId;

    /**
     * 事件编码
     */
    @TableField(value = "eventCode")
    private String eventCode;

    /**
     * 事件类型
     */
    @TableField(value = "eventType")
    private String eventType;

    /**
     * 发生时间
     */
    @TableField(value = "eventTime")
    private Date eventTime;

    /**
     * 告警恢复时间
     */
    @TableField(value = "recoverTime")
    private Date recoverTime;

    /**
     * 事件详情
     */
    @TableField(value = "eventDetail")
    private String eventDetail;

    /**
     * 租户PkID
     */
    @TableField(value = "tenantPkId")
    private String tenantPkId;

    /**
     * 租户编码
     */
    @TableField(value = "tenantCode")
    private String tenantCode;

    /**
     * 租户名称
     */
    @TableField(value = "tenantName")
    private String tenantName;

    /**
     * 租户手机号码
     */
    @TableField(value = "tenantPhone")
    private String tenantPhone;

    /**
     * 仪表PkID
     */
    @TableField(value = "meterPkId")
    private String meterPkId;

    /**
     * 仪表名称
     */
    @TableField(value = "meterName")
    private String meterName;

    /**
     * 仪表序列号
     */
    @TableField(value = "meterSn")
    private String meterSn;

    /**
     * 确认标识（0未确认，1已确认）
     */
    @TableField(value = "confirmFlag")
    private Byte confirmFlag;

    /**
     * 确认时间
     */
    @TableField(value = "confirmTime")
    private Date confirmTime;

    /**
     * 确认描述
     */
    @TableField(value = "confirmDesc")
    private String confirmDesc;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "createUserPkId")
    private String createUserPkId;

    /**
     * 更新时间
     */
    @TableField(value = "modifyTime")
    private Date modifyTime;

    /**
     * 更新人
     */
    @TableField(value = "modifyUserPkId")
    private String modifyUserPkId;
}