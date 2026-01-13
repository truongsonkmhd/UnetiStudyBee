package com.truongsonkmhd.unetistudy.model.lesson;

import com.truongsonkmhd.unetistudy.common.SubmissionVerdict;
import com.truongsonkmhd.unetistudy.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "tbl_coding_submission",
        indexes = {
                @Index(name = "idx_sub_user", columnList = "user_id"),
                @Index(name = "idx_sub_exercise", columnList = "exercise_id"),
                @Index(name = "idx_sub_submitted_at", columnList = "submitted_at"),
                @Index(name = "idx_sub_verdict", columnList = "verdict")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodingSubmission {

    @Id
    @UuidGenerator
    @Column(name = "submission_id", nullable = false, updatable = false)
    UUID submissionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_id", nullable = false)
    CodingExercise exercise;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "code", columnDefinition = "text")
    String code;

    @Column(name = "language", length = 50)
    String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "verdict", length = 10)
    SubmissionVerdict verdict;

    @Column(name = "passed_testcases", nullable = false)
    Integer passedTestcases = 0;

    @Column(name = "total_testcases", nullable = false)
    Integer totalTestcases = 0;

    @Column(name = "runtime_ms")
    Integer runtimeMs;

    @Column(name = "memory_kb")
    Integer memoryKb;

    @CreationTimestamp
    @Column(name = "submitted_at", nullable = false, updatable = false, columnDefinition = "timestamptz")
    Instant submittedAt;
}
