package com.truongsonkmhd.unetistudy.dto.lesson_dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.truongsonkmhd.unetistudy.common.LessonType;
import com.truongsonkmhd.unetistudy.dto.quiz_dto.QuizDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseLessonRequest {

     UUID lessonId; // update thì gửi, create thì null
     UUID moduleId;
     UUID creatorId;

     String title;
     String description;
     String content;
     String videoUrl;

     @JsonIgnore
     MultipartFile videoFile;

     Integer orderIndex;

     Boolean isPreview;
     Boolean isPublished;

     String slug;
     LessonType lessonType;

     Integer totalPoints;

     List<UUID> exerciseTemplateIds;
     List<UUID> quizTemplateIds;
}
