package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.common.TokenType;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface JwtService {
    String generateAccessToken(String username, List<String> authorities);

    String generateRefreshToken(String username, List<String> authorities);

    String extractUsername(String token , TokenType type);
}
