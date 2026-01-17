package com.truongsonkmhd.unetistudy.dto.LessonDTO;

import com.truongsonkmhd.unetistudy.common.LessonType;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.QuizDTO;
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
