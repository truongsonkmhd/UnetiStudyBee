package com.truongsonkmhd.unetistudy.dto.lesson_dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContestShowDTO {
    UUID lessonID;
    String title;
    String description;
    String type;
    Integer duration;
    String image;
    Boolean isPreview;
    String slug;

}
