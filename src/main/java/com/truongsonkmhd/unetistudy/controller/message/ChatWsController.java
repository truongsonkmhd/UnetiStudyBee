package com.truongsonkmhd.unetistudy.controller.message;

import com.truongsonkmhd.unetistudy.dto.websocket.WsMessageEvent;
import com.truongsonkmhd.unetistudy.dto.websocket.WsSendMessageRequest;
import com.truongsonkmhd.unetistudy.model.message.ChatMessage;
import com.truongsonkmhd.unetistudy.sevice.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatWsController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // Client gửi tới: /app/chat.send
    @MessageMapping("/chat.send")
    public void send(WsSendMessageRequest req, Principal principal) {
        UUID me = UUID.fromString(principal.getName());
        UUID roomId = UUID.fromString(req.getRoomId());

        // 1) Lưu DB (có check member trong service)
        ChatMessage saved = chatService.send(me, roomId, req.getText());

        // 2) Broadcast vào room topic
        WsMessageEvent event = new WsMessageEvent(
                saved.getId().toString(),
                roomId.toString(),
                me.toString(),
                saved.getContent(),
                saved.getCreatedAt(),
                req.getClientMsgId()
        );

        messagingTemplate.convertAndSend("/topic/rooms/" + roomId, event);
    }

    // seen: /app/chat.seen
    @MessageMapping("/chat.seen")
    public void seen(Map<String, String> body, Principal principal) {
        UUID me = UUID.fromString(principal.getName());
        UUID roomId = UUID.fromString(body.get("roomId"));
        Instant lastSeenAt = Instant.parse(body.get("lastSeenAt"));
        chatService.seen(me, roomId, lastSeenAt);
    }
}
