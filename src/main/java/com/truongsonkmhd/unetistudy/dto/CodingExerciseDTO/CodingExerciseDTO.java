package com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodingExerciseDTO {
    UUID exerciseID;
    String lessonTitle;
    String title;
    String description;
    String programLanguage;
    String difficulty;
    Integer points;
    String slug;
}