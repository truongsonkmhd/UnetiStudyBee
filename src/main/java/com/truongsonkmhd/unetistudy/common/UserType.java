package com.truongsonkmhd.unetistudy.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserType {
    @JsonProperty
    SYSADMIN,
    @JsonProperty
    ADMIN ,
    @JsonProperty
    TEACHER ,
    @JsonProperty
    STUDENT
}
