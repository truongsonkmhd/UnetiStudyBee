package com.truongsonkmhd.unetistudy.dto.lesson_dto;

import com.truongsonkmhd.unetistudy.common.LessonType;
import com.truongsonkmhd.unetistudy.dto.coding_exercise_dto.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.course_dto.QuizDTO;
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
public class CourseLessonRequest {

     UUID lessonId;      // update thì gửi, create thì null
     UUID moduleId;
     UUID creatorId;

     String title;
     String description;
     String content;
     String videoUrl;

     Integer duration;
     Integer orderIndex;

     Boolean isPreview;
     Boolean isPublished;

     String slug;
     LessonType lessonType;

     Date contestStartTime;
     Date contestEndTime;
     Integer totalPoints;

    // List<CodingExerciseDTO> codingExercises;
     List<UUID> exerciseTemplateIds;  // Add this for METHOD 2
     List<QuizDTO> quizzes;
}
