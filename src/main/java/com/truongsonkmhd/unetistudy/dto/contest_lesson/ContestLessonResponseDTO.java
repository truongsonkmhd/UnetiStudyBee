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

    String title;

    String description;

    Integer defaultDurationMinutes;

    Integer totalPoints;

    Integer defaultMaxAttempts;

    Integer passingScore;

    Boolean showLeaderboardDefault;

    String instructions;

    StatusContest status;

}
