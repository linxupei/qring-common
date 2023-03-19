package com.qring.common.test.repository.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/3/19 19:24
 * @Version 1.0
 */
@Data
@TableName(value = "qr_user")
public class UserDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "mobile")
    private String mobile;

    @TableField(value = "`password`")
    private String password;

    @TableField(value = "`enable`")
    private Boolean enable;

    @TableField(value = "account_non_expired")
    private Boolean accountNonExpired;

    @TableField(value = "account_non_locked")
    private Boolean accountNonLocked;

    @TableField(value = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @TableField(value = "email")
    private String email;
}