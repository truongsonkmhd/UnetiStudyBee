package com.truongsonkmhd.unetistudy.service;

import com.truongsonkmhd.unetistudy.model.message.ChatMessage;
import com.truongsonkmhd.unetistudy.model.message.ChatRoom;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ChatService {
    ChatRoom createOrGetDirect(UUID me, UUID target);

    ChatRoom createGroup(UUID me, String name, List<UUID> memberIds);

    List<ChatRoom> myRooms(UUID me);

    List<ChatMessage> listMessages(UUID me, UUID roomId, Instant before, int size);

    ChatMessage send(UUID me, UUID roomId, String text);

    void seen(UUID me, UUID roomId, Instant lastSeenAt);
}
