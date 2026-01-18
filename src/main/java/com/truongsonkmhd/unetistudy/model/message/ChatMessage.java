package com.truongsonkmhd.unetistudy.model.message;
import lombok.*;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;
@Entity
@Table(name = "tbl_chat_message",
        indexes = @Index(name = "idx_msg_room_created", columnList = "room_id, created_at DESC")
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatMessage {

    public enum MessageType { TEXT, IMAGE, FILE, SYSTEM }

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "room_id", nullable = false, columnDefinition = "uuid")
    private UUID roomId;

    @Column(name = "sender_id", nullable = false, columnDefinition = "uuid")
    private UUID senderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MessageType type;

    @Column(columnDefinition = "text")
    private String content;

    private String attachmentUrl;
    private String attachmentName;
    private String attachmentMime;
    private Long attachmentSize;

    @Column(name = "reply_to_message_id", columnDefinition = "uuid")
    private UUID replyToMessageId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "edited_at")
    private Instant editedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @PrePersist
    void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (createdAt == null) createdAt = Instant.now();
    }
}
