package com.truongsonkmhd.unetistudy.dto.CourseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
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
    String status;
    List<CourseModuleResponse> modules;
}
