package com.truongsonkmhd.unetistudy.dto.response.common;

import com.truongsonkmhd.unetistudy.constant.AppConstant;

public class SuccessResponseMessage extends ResponseMessage {

    // custom \\
    public static ResponseMessage ProcessSuccess(Object ...data){
        return ResponseMessage.builder()
            .status(AppConstant.ResponseConstant.SUCCESS)
            .statusCode(AppConstant.ResponseConstant.StatusCode.SUCCESS)
            .data(data.length == 1 ? data[0] : data)
            .message(AppConstant.ResponseConstant.MessageConstant.SuccessMessage.PROCESS)
            .build();
    }

    public static ResponseMessage CreatedSuccess(Object ...data){
        return ResponseMessage.builder()
            .status(AppConstant.ResponseConstant.SUCCESS)
            .statusCode(AppConstant.ResponseConstant.StatusCode.SUCCESS)
            .data(data.length == 1 ? data[0] : data)
            .message(AppConstant.ResponseConstant.MessageConstant.SuccessMessage.CREATED)
            .build();
    };

    public static ResponseMessage UpdatedSuccess(Object ...data){
        return ResponseMessage.builder()
            .status(AppConstant.ResponseConstant.SUCCESS)
            .statusCode(AppConstant.ResponseConstant.StatusCode.SUCCESS)
            .data(data.length == 1 ? data[0] : data)
            .message(AppConstant.ResponseConstant.MessageConstant.SuccessMessage.UPDATED)
            .build();
    };

    public static ResponseMessage DeletedSuccess(Object ...data){
        return ResponseMessage.builder()
            .status(AppConstant.ResponseConstant.SUCCESS)
            .statusCode(AppConstant.ResponseConstant.StatusCode.SUCCESS)
            .data(data.length == 1 ? data[0] : data)
            .message(AppConstant.ResponseConstant.MessageConstant.SuccessMessage.DELETED)
            .build();
    };

    public static ResponseMessage LoadedSuccess(Object ...data){
        return ResponseMessage.builder()
            .status(AppConstant.ResponseConstant.SUCCESS)
            .statusCode(AppConstant.ResponseConstant.StatusCode.SUCCESS)
            .data(data.length == 1 ? data[0] : data)
            .message(AppConstant.ResponseConstant.MessageConstant.SuccessMessage.LOADED)
            .build();
    };

    public static ResponseMessage ProcessSuccessAndMessage(String message, Object ...data){
        return ResponseMessage.builder()
            .status(AppConstant.ResponseConstant.SUCCESS)
            .statusCode(AppConstant.ResponseConstant.StatusCode.SUCCESS)
            .data(data.length == 1 ? data[0] : data)
            .message(message)
            .build();
    }

}
