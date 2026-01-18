package com.truongsonkmhd.unetistudy.model.message;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tbl_chat_room",
        indexes = {
                @Index(name = "idx_chat_room_last", columnList = "last_message_at DESC"),
                @Index(name = "idx_chat_room_updated", columnList = "updated_at DESC")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_chat_room_direct_key", columnNames = "direct_key")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    public enum RoomType { DIRECT, GROUP }

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RoomType type;

    @Column(length = 255)
    private String name;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "created_by", nullable = false, columnDefinition = "uuid")
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "direct_key")
    private String directKey;

    @Column(name = "last_message_id", columnDefinition = "uuid")
    private UUID lastMessageId;

    @Column(name = "last_message_at")
    private Instant lastMessageAt;

    @PrePersist
    void prePersist() {
        if (id == null) id = UUID.randomUUID();
        Instant now = Instant.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }
}
