package com.truongsonkmhd.unetistudy.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class WsMessageEvent {
    private String messageId;
    private String roomId;
    private String senderId;
    private String text;
    private Instant createdAt;
    private String clientMsgId;
}