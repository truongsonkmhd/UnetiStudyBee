package com.truongsonkmhd.unetistudy.dto.CourseModule;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseModuleFILLDTO {
    String title;
    String slug;

}
