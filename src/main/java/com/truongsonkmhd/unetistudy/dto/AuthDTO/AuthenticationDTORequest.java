package com.truongsonkmhd.unetistudy.dto.AuthDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationDTORequest {
    String username;
    String password;
    Boolean isRememberMe;
}
