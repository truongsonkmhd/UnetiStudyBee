package com.truongsonkmhd.unetistudy.dto.contest_lesson;


import com.truongsonkmhd.unetistudy.common.StatusContest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContestLessonResponseDTO {

    UUID contestLessonId;

    Instant startTime;
    Instant endTime;

    Integer totalPoints;
    Integer maxAttempts;

    Boolean isActive;
    Boolean showLeaderboard;

    StatusContest status; // ONGOING | UPCOMING | ENDED

}
