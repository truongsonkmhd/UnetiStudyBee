package com.truongsonkmhd.unetistudy.model.lesson.solid.course_lesson;

import com.truongsonkmhd.unetistudy.common.StatusContest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_contest_lesson",
        indexes = {
                @Index(name = "idx_contest_time", columnList = "start_time, end_time"),
                @Index(name = "idx_contest_active", columnList = "is_active")
        })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContestLesson {

    @Id
    @UuidGenerator
    @Column(name = "contest_lesson_id", nullable = false, updatable = false)
    UUID contestLessonId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contest_lesson_id", nullable = false)
    CourseLesson courseLesson;

    @OneToMany(mappedBy = "contestLesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<CodingExercise> codingExercises = new ArrayList<>();

    @OneToMany(mappedBy = "contestLesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<QuizQuestion> quizQuestions = new ArrayList<>();

    @Column(name = "start_time", nullable = false)
    Instant startTime;

    @Column(name = "end_time", nullable = false)
    Instant endTime;

    @Column(name = "total_points", nullable = false, columnDefinition = "INT DEFAULT 0")
    @Builder.Default
    Integer totalPoints = 0;

    @Column(name = "max_attempts")
    Integer maxAttempts;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    Boolean isActive = true;

    @Column(name = "show_leaderboard", nullable = false)
    @Builder.Default
    Boolean showLeaderboard = true;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    StatusContest status;

    // Helper methods for bidirectional relationships
    public void addCodingExercise(CodingExercise exercise) {
        codingExercises.add(exercise);
        exercise.setContestLesson(this);
    }

    public void removeCodingExercise(CodingExercise exercise) {
        codingExercises.remove(exercise);
        exercise.setContestLesson(null);
    }

    public void addQuizQuestion(QuizQuestion quiz) {
        quizQuestions.add(quiz);
        quiz.setContestLesson(this);
    }

    public void removeQuizQuestion(QuizQuestion quiz) {
        quizQuestions.remove(quiz);
        quiz.setContestLesson(null);
    }

    // Business logic
    public boolean isOngoing(Instant now) {
        return isActive && now.isAfter(startTime) && now.isBefore(endTime);
    }

    public boolean isUpcoming(Instant now) {
        return isActive && now.isBefore(startTime);
    }

    public boolean isEnded(Instant now) {
        return !isActive || now.isAfter(endTime);
    }
}