package com.truongsonkmhd.unetistudy.dto.custom.request.user;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class UserPasswordRequest implements Serializable {
    private UUID id;
    private String password;
    private String confirmPassword;
}
