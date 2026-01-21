package com.truongsonkmhd.unetistudy.dto.contest_lesson;


import com.truongsonkmhd.unetistudy.common.ClassContestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassContestResponse {

    UUID classContestId;

    // Thông tin lớp học
    ClassInfo classInfo;

    // Thông tin contest template
    ContestInfo contestInfo;

    // Thời gian thi
    Instant scheduledStartTime;
    Instant scheduledEndTime;
    Long durationInMinutes;

    // Cấu hình
    ClassContestStatus status;
    Boolean isActive;
    Double weight;

    // Cấu hình hiệu quả (đã áp dụng override)
    EffectiveConfig effectiveConfig;

    Instant createdAt;
    Instant updatedAt;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassInfo {
        UUID classId;
        String classCode;
        String className;
        String instructorName;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContestInfo {
        UUID contestLessonId;
        String title; // Từ CourseLesson
        String description;
        Integer defaultTotalPoints;
        Integer codingExerciseCount;
        Integer quizQuestionCount;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EffectiveConfig {
        Integer maxAttempts;
        Boolean showLeaderboard;
        String instructions;
        Integer passingScore;
        Integer totalPoints; // Đã áp dụng weight
    }
}