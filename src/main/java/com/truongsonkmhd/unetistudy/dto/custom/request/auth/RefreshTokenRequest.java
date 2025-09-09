package com.truongsonkmhd.unetistudy.dto.custom.request.auth;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class RefreshTokenRequest {
    String refreshToken;
}
