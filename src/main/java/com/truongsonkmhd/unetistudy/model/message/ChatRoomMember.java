package com.truongsonkmhd.unetistudy.model.message;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tbl_chat_room_member",
        indexes = @Index(name = "idx_member_user", columnList = "user_id, room_id")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomMember {

    public enum Role { MEMBER, ADMIN }

    @EmbeddedId
    private ChatRoomMemberId id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;

    @Column(name = "last_seen_at")
    private Instant lastSeenAt;

    @Column(nullable = false)
    private boolean muted;

    @PrePersist
    void prePersist() {
        if (joinedAt == null) joinedAt = Instant.now();
    }
}