package com.truongsonkmhd.unetistudy.dto.contest_lesson;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContestLessonRequestDTO {


    Instant startTime;
    Instant endTime;

    Integer maxAttempts;
    Boolean isActive;
    Boolean showLeaderboard;

    List<UUID> exerciseTemplateIds;

}
