package com.truongsonkmhd.unetistudy.dto.custom.response.auth;

import com.truongsonkmhd.unetistudy.dto.custom.response.user.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AuthenticationResponse {
     Boolean isAuthenticated;
     String token;
     String refreshToken;
     UserResponse user;
}

