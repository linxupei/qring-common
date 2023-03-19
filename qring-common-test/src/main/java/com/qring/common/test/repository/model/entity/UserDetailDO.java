package com.qring.common.test.repository.model.entity;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Author Qring
 * @Description TODO
 * @Date 2023/3/19 19:45
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password", "user", "roles"})
public class UserDetailDO implements UserDetails, Serializable {

    private UserDO user;

    private List<RoleDO> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (ObjectUtil.isEmpty(roles)) {
            return Collections.emptyList();
        }
        List<GrantedAuthority> res = new ArrayList<>();
        roles.forEach(role -> res.add((GrantedAuthority) role::getRoleName));
        return res;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return ObjectUtil.equal(user.getAccountNonExpired(), Boolean.TRUE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return ObjectUtil.equal(user.getAccountNonLocked(), Boolean.TRUE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return ObjectUtil.equal(user.getCredentialsNonExpired(), Boolean.TRUE);
    }

    @Override
    public boolean isEnabled() {
        return ObjectUtil.equal(user.getEnable(), Boolean.TRUE);
    }
}
