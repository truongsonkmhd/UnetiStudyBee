package com.truongsonkmhd.unetistudy.model.lesson;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "tbl_coding_exercise",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_exercise_lesson_slug", columnNames = {"lesson_id", "slug"})
        },
        indexes = {
                @Index(name = "idx_exercise_lesson", columnList = "lesson_id"),
                @Index(name = "idx_exercise_lesson_difficulty", columnList = "lesson_id,difficulty")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodingExercise {

    @Id
    @UuidGenerator
    @Column(name = "exercise_id", nullable = false, updatable = false)
    UUID exerciseId;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    CourseLesson lesson;

    @OneToMany(
            mappedBy = "codingExercise",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    List<ExerciseTestCase> exerciseTestCases = new ArrayList<>();


    @Column(name = "title", length = 255, nullable = false)
    String title;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @Column(name = "programming_language", length = 50)
    String programmingLanguage;

    @Column(name = "initial_code", columnDefinition = "text")
    String initialCode;

    @Column(name = "solution_code", columnDefinition = "text")
    String solutionCode;

    @Column(name = "time_limit_ms", nullable = false, columnDefinition = "INT DEFAULT 1000")
    Integer timeLimitMs;

    @Column(name = "memory_limit_mb", nullable = false, columnDefinition = "INT DEFAULT 256")
    Integer memoryLimitMb;

    @Column(name = "difficulty", length = 20, nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'medium'")
    String difficulty;

    @Column(name = "points", nullable = false, columnDefinition = "INT DEFAULT 0")
    Integer points;

    @Column(name = "slug")
    String slug;

    @Column(name = "input_format", columnDefinition = "text")
    String inputFormat;

    @Column(name = "output_format", columnDefinition = "text")
    String outputFormat;

    @Column(name = "constraint_name", length = 50)
    String constraintName;

    @Column(name = "is_published", nullable = false, columnDefinition = "boolean default false")
    Boolean isPublished = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    Instant updatedAt;


    public void addTestCase(ExerciseTestCase testCase) {
        exerciseTestCases.add(testCase);
        testCase.setCodingExercise(this);
    }


}
