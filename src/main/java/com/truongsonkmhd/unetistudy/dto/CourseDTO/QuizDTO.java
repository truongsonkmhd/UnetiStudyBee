package com.truongsonkmhd.unetistudy.dto.CourseDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizDTO {
    UUID quizId;
    UUID lessonId;
    String title;
    Integer totalQuestions;
    Double passScore;
    Boolean isPublished;
}
