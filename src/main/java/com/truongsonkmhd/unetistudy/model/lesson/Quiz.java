package com.truongsonkmhd.unetistudy.model.lesson;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "tbl_quiz",
        indexes = {
                @Index(name = "idx_quiz_lesson", columnList = "lesson_id")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Quiz {

    @Id
    @UuidGenerator
    @Column(name = "quiz_id", nullable = false, updatable = false)
    UUID quizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    CourseLesson lesson;

    @Column(name = "title", nullable = false, length = 255)
    String title;

    @Column(name = "total_questions", nullable = false)
    Integer totalQuestions = 0;

    @Column(name = "pass_score")
    Double passScore;

    @Column(name = "is_published", nullable = false)
    Boolean isPublished = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    Instant updatedAt;
}
