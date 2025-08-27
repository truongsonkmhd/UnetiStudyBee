package com.truongsonkmhd.unetistudy.dto.response.auth;

import com.truongsonkmhd.unetistudy.dto.response.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    public Boolean isAuthenticated;
    public String token;
    public String refreshToken;
    private UserResponse user;
}

