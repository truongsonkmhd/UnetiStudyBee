package com.truongsonkmhd.unetistudy.dto.CourseDTO;

import com.truongsonkmhd.unetistudy.common.LessonType;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseLessonResponse {
    UUID lessonId;
    String title;
    Integer orderIndex;
    LessonType lessonType;
    Boolean isPreview;
    Boolean isPublished;
    List<CodingExerciseDTO> codingExercises;
    List<QuizDTO> quizzes;
}
