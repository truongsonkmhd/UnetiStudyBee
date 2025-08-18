package com.truongsonkmhd.unetistudy.dto.request;

import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
public class UserPasswordRequestDTO implements Serializable {
    private UUID id;
    private String password;
    private String confirmPassword;
}
