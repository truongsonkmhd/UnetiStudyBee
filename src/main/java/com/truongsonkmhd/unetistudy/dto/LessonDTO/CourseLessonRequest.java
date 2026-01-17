package com.truongsonkmhd.unetistudy.dto.LessonDTO;

import com.truongsonkmhd.unetistudy.common.LessonType;
import com.truongsonkmhd.unetistudy.dto.CodingExerciseDTO.CodingExerciseDTO;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.QuizDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseLessonRequest {

     UUID lessonId;          // tham chiếu đến module chứa bài học
     UUID moduleId;          // tham chiếu đến module chứa bài học
     UUID creatorId;          // tham chiếu đến module chứa bài học

     String title;           // tiêu đề bài học
     String description;     // mô tả bài học
     String content;         // nội dung
     String videoUrl;        // link video (nếu có)
     Integer duration;       // thời lượng (phút hoặc giây)
     Integer orderIndex;     // thứ tự bài học
     Boolean isPreview;      // cho phép học thử
     Boolean isPublished;    // đã public chưa
     String slug;
     LessonType lessonType;

     Boolean isContest;      // có phải contest không
     Integer totalPoints;    // tổng điểm (cho quiz/contest)
     Date contestStartTime; // thời gian bắt đầu contest
     Date contestEndTime;   // thời gian kết thúc contest

     @Builder.Default // neu khong co ccai nay thi khi = new ArrayList<>() mac dinh van la null
     List<CodingExerciseDTO> codingExercises = new  ArrayList<>();

     @Builder.Default
     List<QuizDTO> quizzes =  new  ArrayList<>();
}
