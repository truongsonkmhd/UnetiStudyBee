package com.truongsonkmhd.unetistudy.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(MyUserDetail myUserDetail,  boolean isRememberMe);

    String generateRefreshToken(MyUserDetail myUserDetail, boolean isRememberMe);

    String extractUsername(String token);
}
