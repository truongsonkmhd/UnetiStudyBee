package com.truongsonkmhd.unetistudy.dto.custom.response.course;

import com.truongsonkmhd.unetistudy.dto.custom.response.lesson.LessonResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
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
    LocalDateTime createdAt;     // Ngày tạo
    LocalDateTime updatedAt;     // Ngày update

    List<LessonResponse> lessons; // Danh sách bài học
}
