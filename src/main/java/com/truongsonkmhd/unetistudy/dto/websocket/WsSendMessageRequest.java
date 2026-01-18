package com.truongsonkmhd.unetistudy.dto.websocket;

import lombok.Data;

@Data
public class WsSendMessageRequest {
    private String roomId;     // UUID string
    private String text;
    private String clientMsgId; // để client match optimistic UI
}
