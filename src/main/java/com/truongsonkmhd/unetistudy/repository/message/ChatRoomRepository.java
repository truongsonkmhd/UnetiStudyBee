package com.truongsonkmhd.unetistudy.repository.message;

import com.truongsonkmhd.unetistudy.model.message.ChatRoom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    Optional<ChatRoom> findByDirectKey(String directKey);

    @Query("""
    select r from ChatRoom r
    join ChatRoomMember m on m.id.roomId = r.id
    where m.id.userId = :userId
    order by coalesce(r.lastMessageAt, r.updatedAt) desc
  """)
    List<ChatRoom> findMyRooms(@Param("userId") UUID userId);
}
