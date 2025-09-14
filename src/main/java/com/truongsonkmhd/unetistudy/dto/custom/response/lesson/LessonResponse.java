package com.truongsonkmhd.unetistudy.dto.custom.response.lesson;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResponse {
     private String title;
     private String description;
     private String type;
     private Integer duration;
     private String image;
     private Boolean isPreview;
     private String slug;
     private LocalDateTime contestStartTime;
     private LocalDateTime contestEndTime;
}
