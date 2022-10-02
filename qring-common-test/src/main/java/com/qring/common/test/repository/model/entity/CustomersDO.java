package com.qring.common.test.repository.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* @Author Qring
* @Description TODO
* @Date 2022/8/18 14:28
* @Version 1.0
*/

@Data
@TableName(value = "customers")
public class CustomersDO {
    @TableId(value = "cust_id", type = IdType.INPUT)
    private Integer custId;

    @TableField(value = "cust_name")
    private String custName;

    @TableField(value = "cust_address")
    private String custAddress;

    @TableField(value = "cust_city")
    private String custCity;

    @TableField(value = "cust_state")
    private String custState;

    @TableField(value = "cust_zip")
    private String custZip;

    @TableField(value = "cust_country")
    private String custCountry;

    @TableField(value = "cust_contact")
    private String custContact;

    @TableField(value = "cust_email")
    private String custEmail;
}