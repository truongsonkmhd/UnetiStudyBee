package com.truongsonkmhd.unetistudy.dto.custom.request.lesson;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonRequest {
     UUID moduleId;      // tham chiếu đến module chứa bài học

     String title;       // tiêu đề bài học
     String description; // mô tả bài học
     String type;        // loại (video, text, quiz...)
     String content;     // nội dung
     String videoUrl;    // link video (nếu có)
     Integer duration;   // thời lượng (phút hoặc giây)
     Integer orderIndex; // thứ tự bài học
     Boolean isPreview;  // cho phép học thử hay không
     Boolean isPublished;// đã public chưa
}
