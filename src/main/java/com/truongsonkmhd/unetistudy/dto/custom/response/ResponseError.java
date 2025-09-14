package com.truongsonkmhd.unetistudy.dto.custom.response;

public class ResponseError extends ResponseData {

    public ResponseError(int status, String message) {
        super(status, message);
    }
}
