package com.truongsonkmhd.unetistudy.dto.LessonDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditLessonRequest {
    UUID lessonID;
    String title;
    String description;
    String image;
    Integer duration;
    String type;
    Boolean isContest;
    Date contestStartTime;
    Date contestEndTime;
    String slug;
}
