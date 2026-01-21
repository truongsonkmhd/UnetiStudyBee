package com.truongsonkmhd.unetistudy.sevice.impl.message;

import com.truongsonkmhd.unetistudy.model.message.ChatMessage;
import com.truongsonkmhd.unetistudy.model.message.ChatRoom;
import com.truongsonkmhd.unetistudy.model.message.ChatRoomMember;
import com.truongsonkmhd.unetistudy.model.message.ChatRoomMemberId;
import com.truongsonkmhd.unetistudy.repository.message.ChatMessageRepository;
import com.truongsonkmhd.unetistudy.repository.message.ChatRoomMemberRepository;
import com.truongsonkmhd.unetistudy.repository.message.ChatRoomRepository;
import com.truongsonkmhd.unetistudy.sevice.ChatService;
import helper.ChatKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository roomRepo;
    private final ChatRoomMemberRepository memberRepo;
    private final ChatMessageRepository msgRepo;

    @Override
    @Transactional
    public ChatRoom createOrGetDirect(UUID me, UUID target) {
        if (target == null || me.equals(target)) throw new IllegalArgumentException("target invalid");

        String key = ChatKeys.directKey(me, target);

        // Fast path
        return roomRepo.findByDirectKey(key).orElseGet(() -> {
            ChatRoom room = ChatRoom.builder()
                    .type(ChatRoom.RoomType.DIRECT)
                    .directKey(key)
                    .createdBy(me)
                    .build();

            try {
                room = roomRepo.save(room);
            } catch (DataIntegrityViolationException e) {
                // Race condition: 2 requests create same direct room -> re-fetch
                return roomRepo.findByDirectKey(key)
                        .orElseThrow(() -> e);
            }

            upsertMember(room.getId(), me, ChatRoomMember.Role.MEMBER);
            upsertMember(room.getId(), target, ChatRoomMember.Role.MEMBER);
            return room;
        });
    }

    @Override
    @Transactional
    public ChatRoom createGroup(UUID me, String name, List<UUID> memberIds) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name required");

        // unique members
        LinkedHashSet<UUID> set = new LinkedHashSet<>();
        if (memberIds != null) set.addAll(memberIds);
        set.add(me);

        if (set.size() < 3) throw new IllegalArgumentException("group must have >= 3 members");

        ChatRoom room = ChatRoom.builder()
                .type(ChatRoom.RoomType.GROUP)
                .name(name)
                .createdBy(me)
                .build();
        room = roomRepo.save(room);

        upsertMember(room.getId(), me, ChatRoomMember.Role.ADMIN);
        for (UUID u : set) {
            if (!u.equals(me)) upsertMember(room.getId(), u, ChatRoomMember.Role.MEMBER);
        }
        return room;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoom> myRooms(UUID me) {
        return roomRepo.findMyRooms(me);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> listMessages(UUID me, UUID roomId, Instant before, int size) {
        ensureMember(roomId, me);
        int pageSize = Math.min(Math.max(size, 1), 100);
        Pageable p = PageRequest.of(0, pageSize);

        if (before == null) return msgRepo.findLatest(roomId, p);
        return msgRepo.findBefore(roomId, before, p);
    }

    @Override
    @Transactional
    public ChatMessage send(UUID me, UUID roomId, String text) {
        ensureMember(roomId, me);
        if (text == null || text.isBlank()) throw new IllegalArgumentException("text required");

        Instant now = Instant.now();
        ChatMessage msg = ChatMessage.builder()
                .roomId(roomId)
                .senderId(me)
                .type(ChatMessage.MessageType.TEXT)
                .content(text)
                .createdAt(now)
                .build();
        msg = msgRepo.save(msg);

        // Update room last_message (constant-time list rooms)
        ChatRoom room = roomRepo.findById(roomId).orElseThrow();
        room.setLastMessageId(msg.getId());
        room.setLastMessageAt(now);
        room.setUpdatedAt(now);
        roomRepo.save(room);

        return msg;
    }

    @Override
    @Transactional
    public void seen(UUID me, UUID roomId, Instant lastSeenAt) {
        ensureMember(roomId, me);
        ChatRoomMember m = memberRepo.findByIdRoomIdAndIdUserId(roomId, me).orElseThrow();
        m.setLastSeenAt(lastSeenAt != null ? lastSeenAt : Instant.now());
        memberRepo.save(m);
    }

    private void ensureMember(UUID roomId, UUID userId) {
        if (!memberRepo.existsByIdRoomIdAndIdUserId(roomId, userId)) {
            throw new SecurityException("not a member of room");
        }
    }

    private void upsertMember(UUID roomId, UUID userId, ChatRoomMember.Role role) {
        ChatRoomMemberId id = new ChatRoomMemberId(roomId, userId);
        ChatRoomMember m = memberRepo.findById(id).orElseGet(() ->
                ChatRoomMember.builder().id(id).muted(false).build()
        );
        m.setRole(role);
        memberRepo.save(m);
    }
}
