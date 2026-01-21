package com.truongsonkmhd.unetistudy.model.lesson.course_lesson;

import com.truongsonkmhd.unetistudy.model.quiz.Question;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_quiz",
        indexes = {
                @Index(name = "idx_quiz_contest", columnList = "contest_lesson_id")
        })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizQuestion {

    @Id
    @UuidGenerator
    @Column(name = "quiz_id", nullable = false, updatable = false)
    UUID quizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_lesson_id", nullable = false)
    ContestLesson contestLesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    CourseLesson courseLesson;

    @OneToMany(mappedBy ="quiz" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    @Builder.Default
    List<Question> questions = new ArrayList<>();

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "total_questions")
    @Builder.Default
    Integer totalQuestions = 0;

    @Column(name = "pass_score")
    Double passScore;

    @Column(name = "is_published", nullable = false)
    @Builder.Default
    Boolean isPublished = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Instant updatedAt;
}