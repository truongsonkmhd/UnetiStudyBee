package com.truongsonkmhd.unetistudy.dto.LessonDTO;

import com.truongsonkmhd.unetistudy.common.LessonType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseLessonResponse {
     String title;
     String description;
     String type;
     Integer duration;
     String image;
     Boolean isPreview;
     String slug;
     LessonType lessonType;

     LocalDateTime contestStartTime;
     LocalDateTime contestEndTime;
}
