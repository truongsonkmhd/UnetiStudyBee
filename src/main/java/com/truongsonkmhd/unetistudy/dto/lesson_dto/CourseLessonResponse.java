package com.truongsonkmhd.unetistudy.dto.lesson_dto;

import com.truongsonkmhd.unetistudy.common.LessonType;
import com.truongsonkmhd.unetistudy.dto.coding_exercise_dto.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.quiz_dto.QuizDTO;
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
public class CourseLessonResponse {
     UUID lessonId;
     String title;
     String description;
     String type;
     Integer duration;
     Integer orderIndex;
     String image;
     Boolean isPreview;
     Boolean isPublished;
     String slug;
     String videoUrl;

     public String getFileUrl() {
          return videoUrl;
     }

     LessonType lessonType;
     Integer totalPoints;

     List<CodingExerciseDTO> codingExercises;
     List<QuizDTO> quizzes;
}
