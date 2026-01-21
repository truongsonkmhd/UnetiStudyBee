package com.truongsonkmhd.unetistudy.dto.course_dto;

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
