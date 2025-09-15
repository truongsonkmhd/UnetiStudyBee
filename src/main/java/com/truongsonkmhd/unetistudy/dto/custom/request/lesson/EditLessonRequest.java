package com.truongsonkmhd.unetistudy.dto.custom.request.lesson;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditLessonRequest {
    Long lessonID;
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
