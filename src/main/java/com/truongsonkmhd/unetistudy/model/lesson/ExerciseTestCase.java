package com.truongsonkmhd.unetistudy.model.lesson;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_exercise_test_case")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ExerciseTestCase {

    @Id
    @UuidGenerator
    @Column(name = "test_case_id", nullable = false, updatable = false)
    UUID testCaseID;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    CodingExercise codingExercise;

    @Column(name = "input")
    String input;

    @Column(name = "expected_output")
    String expectedOutput;

    @Column(name = "is_public")
    Boolean isPublic;

    @Column(name = "score")
    Integer score;

    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

}
