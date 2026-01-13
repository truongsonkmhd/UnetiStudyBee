package com.truongsonkmhd.unetistudy.dto.CourseDTO;

import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseLessonResponse {
    UUID lessonId;
    String title;
    String type;
    Integer orderIndex;
    Boolean isPreview;
    Boolean isPublished;
    List<CodingExerciseDTO> codingExercises;
    List<QuizDTO> quizzes;
}
