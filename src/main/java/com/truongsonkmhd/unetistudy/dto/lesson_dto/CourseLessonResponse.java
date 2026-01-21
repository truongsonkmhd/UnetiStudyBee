package com.truongsonkmhd.unetistudy.dto.lesson_dto;

import com.truongsonkmhd.unetistudy.common.LessonType;
import com.truongsonkmhd.unetistudy.dto.coding_exercise_dto.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.course_dto.QuizDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseLessonResponse {
     String title;
     String description;
     String type;
     Integer duration;
     String image;
     Boolean isPreview;
     String slug;
     LessonType lessonType;

     LocalDateTime contestStartTime;
     LocalDateTime contestEndTime;

     List<CodingExerciseDTO> codingExercises;
     List<QuizDTO> quizzes;
}
