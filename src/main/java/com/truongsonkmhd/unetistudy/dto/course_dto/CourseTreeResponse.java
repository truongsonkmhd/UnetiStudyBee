package com.truongsonkmhd.unetistudy.dto.course_dto;

import com.truongsonkmhd.unetistudy.common.CourseStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseTreeResponse {
    UUID courseId;
    String title;
    String slug;
    String description;
    Boolean isPublished;
    CourseStatus status;
    List<CourseModuleResponse> modules;
}
