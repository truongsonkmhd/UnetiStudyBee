package com.truongsonkmhd.unetistudy.repository.message;

import com.truongsonkmhd.unetistudy.model.message.ChatMessage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    @Query("""
    select msg from ChatMessage msg
    where msg.roomId = :roomId
      and msg.deletedAt is null
    order by msg.createdAt desc
  """)
    List<ChatMessage> findLatest(@Param("roomId") UUID roomId, Pageable pageable);

    @Query("""
    select msg from ChatMessage msg
    where msg.roomId = :roomId
      and msg.deletedAt is null
      and msg.createdAt < :before
    order by msg.createdAt desc
  """)
    List<ChatMessage> findBefore(@Param("roomId") UUID roomId, @Param("before") Instant before, Pageable pageable);
}

