package com.truongsonkmhd.unetistudy.dto.AuthDTO;

import com.truongsonkmhd.unetistudy.dto.UserDTO.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationDTOResponse {
     Boolean isAuthenticated;
     String token;
     String refreshToken;
     UserResponse user;
}

