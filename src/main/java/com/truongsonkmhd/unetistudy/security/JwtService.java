package com.truongsonkmhd.unetistudy.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails ,  boolean isRememberMe);

    String generateRefreshToken(UserDetails userDetails, boolean isRememberMe);

    String extractUsername(String token);
}
