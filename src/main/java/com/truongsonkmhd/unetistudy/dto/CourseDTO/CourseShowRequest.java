package com.truongsonkmhd.unetistudy.dto.CourseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseShowRequest {
    String title;             // Tiêu đề khóa học
    String description;       // Mô tả chi tiết
    String shortDescription;  // Mô tả ngắn
    UUID instructorId;        // Id giảng viên (nếu FE chỉ gửi id)
    String level;             // Trình độ
    String category;          // Danh mục chính
    String subCategory;       // Danh mục phụ
    Integer duration;         // Thời lượng (phút)
    Integer capacity;         // Sức chứa
    Integer enrolledCount = 0;
    String imageUrl;          // Ảnh đại diện
    String videoUrl;          // Video giới thiệu
    String requirements;      // Yêu cầu đầu vào
    String objectives;        // Mục tiêu học tập
    String status;
    String syllabus;          // Đề cương
    Boolean isPublished;      // Có xuất bản ngay không
    LocalDateTime publishedAt;

    @Builder.Default // neu khong co ccai nay thi khi = new ArrayList<>() mac dinh van la null
    List<CourseModuleRequest> modules = new ArrayList<>();
}
