package com.truongsonkmhd.unetistudy.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_coding_exercise")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CodingExercise {

    @Id
    @UuidGenerator
    @Column(name = "exercise_id", nullable = false, updatable = false)
    UUID exerciseId;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    CourseLesson lesson;

    @OneToMany(mappedBy = "codingExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ExerciseTestCase> exerciseTestCases;

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

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    Date createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    Date updatedAt;

}
