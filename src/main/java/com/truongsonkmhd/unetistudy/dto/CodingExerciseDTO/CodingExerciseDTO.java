package com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodingExerciseDTO {
    UUID exerciseId;
    UUID lessonId;

    String title;
    String description;

    String programmingLanguage;
    String difficulty;

    Integer points;
    Boolean isPublished;

    Integer timeLimitMs;
    Integer memoryLimitMb;

    String slug;
    String inputFormat;
    String outputFormat;
    String constraintName;

    Instant createdAt;
    Instant updatedAt;
}