package com.truongsonkmhd.unetistudy.dto.CourseDTO;

import com.truongsonkmhd.unetistudy.dto.LessonDTO.LessonResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseModuleResponse {
    UUID moduleId;               // ID module
    String title;                // Tiêu đề module
    String description;          // Mô tả
    Integer orderIndex;          // Thứ tự trong khóa học
    Integer duration;            // Thời lượng (tổng)
    Boolean isPublished;         // Trạng thái publish
    Date createdAt;     // Ngày tạo
    Date updatedAt;     // Ngày update

    List<LessonResponse> lessons; // Danh sách bài học
}
