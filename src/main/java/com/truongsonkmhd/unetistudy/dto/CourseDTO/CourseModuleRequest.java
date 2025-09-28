package com.truongsonkmhd.unetistudy.dto.CourseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseModuleRequest  {
     String title;        // Tiêu đề module
     String description;  // Mô tả chi tiết
     Integer orderIndex;  // Thứ tự module trong khóa học
     Integer duration;    // Thời lượng (tổng phút/giờ)
     Boolean isPublished; // Trạng thái publish
}
