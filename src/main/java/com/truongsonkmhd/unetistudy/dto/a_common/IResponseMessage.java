package com.truongsonkmhd.unetistudy.dto.a_common;

public interface IResponseMessage {
    boolean status = true;
    int statusCode = 200;
    String message = "";
    Object data = new Object();
}
