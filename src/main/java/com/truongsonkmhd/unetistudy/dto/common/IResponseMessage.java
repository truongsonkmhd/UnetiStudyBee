package com.truongsonkmhd.unetistudy.dto.common;

public interface IResponseMessage {
    boolean status = true;
    int statusCode = 200;
    String message = "";
    Object data = new Object();
}
