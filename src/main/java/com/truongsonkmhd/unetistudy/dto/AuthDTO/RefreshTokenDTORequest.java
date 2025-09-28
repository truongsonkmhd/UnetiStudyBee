package com.truongsonkmhd.unetistudy.dto.AuthDTO;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class RefreshTokenDTORequest {
    String refreshToken;
}
