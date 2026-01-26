package com.truongsonkmhd.unetistudy.controller.message;

import com.truongsonkmhd.unetistudy.model.message.ChatMessage;
import com.truongsonkmhd.unetistudy.model.message.ChatRoom;
import com.truongsonkmhd.unetistudy.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class ChatController {

    private final ChatService chatService;

    // MVP: lấy userId từ header để test nhanh (sau thay bằng JWT sub)
    private UUID me(String header) {
        if (header == null || header.isBlank())
            throw new IllegalArgumentException("Missing X-User-Id");
        return UUID.fromString(header);
    }

    @PostMapping("/rooms/direct")
    public ChatRoom direct(@RequestHeader("X-User-Id") String userId,
            @RequestBody Map<String, String> body) {
        UUID target = UUID.fromString(body.get("targetUserId"));
        return chatService.createOrGetDirect(me(userId), target);
    }

    @PostMapping("/rooms/group")
    public ChatRoom group(@RequestHeader("X-User-Id") String userId,
            @RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        @SuppressWarnings("unchecked")
        List<String> members = (List<String>) body.get("memberIds");
        List<UUID> ids = members == null ? List.of() : members.stream().map(UUID::fromString).toList();
        return chatService.createGroup(me(userId), name, ids);
    }

    @GetMapping("/rooms")
    public List<ChatRoom> rooms(@RequestHeader("X-User-Id") String userId) {
        return chatService.myRooms(me(userId));
    }

    @GetMapping("/rooms/{roomId}/messages")
    public List<ChatMessage> messages(@RequestHeader("X-User-Id") String userId,
            @PathVariable UUID roomId,
            @RequestParam(required = false) Instant before,
            @RequestParam(defaultValue = "50") int size) {
        return chatService.listMessages(me(userId), roomId, before, size);
    }

    @PostMapping("/rooms/{roomId}/messages")
    public ChatMessage send(@RequestHeader("X-User-Id") String userId,
            @PathVariable UUID roomId,
            @RequestBody Map<String, String> body) {
        return chatService.send(me(userId), roomId, body.get("text"));
    }

    @PostMapping("/rooms/{roomId}/seen")
    public void seen(@RequestHeader("X-User-Id") String userId,
            @PathVariable UUID roomId,
            @RequestBody Map<String, String> body) {
        Instant t = Instant.parse(body.get("lastSeenAt"));
        chatService.seen(me(userId), roomId, t);
    }
}
