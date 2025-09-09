package com.truongsonkmhd.unetistudy.dto.common;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
@Builder
public class ResponseMessage implements IResponseMessage {
    boolean status = true;
    int statusCode = 200;
    String message = "";
    Object data;
}
