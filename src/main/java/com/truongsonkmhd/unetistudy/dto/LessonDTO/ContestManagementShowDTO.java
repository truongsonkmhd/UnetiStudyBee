package com.truongsonkmhd.unetistudy.dto.LessonDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContestManagementShowDTO {
    String title;
    String slug;
    String userNameOwner;
    LocalDateTime contestStartTime;
    LocalDateTime contestEndTime;
}
