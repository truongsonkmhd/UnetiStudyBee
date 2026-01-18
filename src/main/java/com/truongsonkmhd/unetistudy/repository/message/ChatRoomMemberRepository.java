package com.truongsonkmhd.unetistudy.repository.message;
import com.truongsonkmhd.unetistudy.model.message.ChatRoomMember;
import com.truongsonkmhd.unetistudy.model.message.ChatRoomMemberId;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, ChatRoomMemberId> {

    boolean existsByIdRoomIdAndIdUserId(UUID roomId, UUID userId);

    Optional<ChatRoomMember> findByIdRoomIdAndIdUserId(UUID roomId, UUID userId);

    @Query("select m.id.userId from ChatRoomMember m where m.id.roomId = :roomId")
    List<UUID> findMemberIds(@Param("roomId") UUID roomId);
}
