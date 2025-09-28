package com.truongsonkmhd.unetistudy.dto.AuthDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenDTOResponse {
    private String accessToken;
    private String refreshToken;
}
