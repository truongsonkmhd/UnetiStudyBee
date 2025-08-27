package com.truongsonkmhd.unetistudy.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntrospectResponse {
    private boolean isValid;
    private String username;
    private UUID id;
}
