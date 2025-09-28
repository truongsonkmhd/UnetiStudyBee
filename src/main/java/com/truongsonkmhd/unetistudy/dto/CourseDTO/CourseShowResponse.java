package com.truongsonkmhd.unetistudy.dto.CourseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseShowResponse {
    String title;
    String description;
    Integer duration;
    Boolean isPublished;
    private List<CourseModuleResponse> modules;
}
