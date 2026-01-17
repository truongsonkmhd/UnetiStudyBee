package com.truongsonkmhd.unetistudy.model.lesson;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_exercise_test_case")
public class ExerciseTestCase {

    @Id
    @UuidGenerator
    @Column(name = "test_case_id", nullable = false, updatable = false)
    UUID testCaseID;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_id", nullable = false)
    CodingExercise codingExercise;

    @Column(name = "input")
    String input;

    @Column(name = "expected_output")
    String expectedOutput;

    @Column(name = "is_public")
    Boolean isPublic;

    @Column(name = "score")
    Integer score;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;
}
