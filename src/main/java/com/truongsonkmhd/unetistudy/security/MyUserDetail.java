package com.truongsonkmhd.unetistudy.security;

import com.truongsonkmhd.unetistudy.common.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;


import com.truongsonkmhd.unetistudy.model.User;


public record MyUserDetail(User user) implements UserDetails, Serializable {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return  null;
    }

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
        return true; // hoặc check logic riêng nếu bạn muốn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // có thể map từ UserStatus hoặc field khác
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // có thể map từ refreshTokenExpirationTime nếu cần
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(user.getStatus());
    }


}
