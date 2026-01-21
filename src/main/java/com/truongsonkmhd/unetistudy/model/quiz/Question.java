package com.truongsonkmhd.unetistudy.model.quiz;

import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.QuizQuestion;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
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
@Table(name = "tbl_question",
        indexes = {
                @Index(name = "idx_question_quiz", columnList = "quiz_id")
        })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {

    @Id
    @UuidGenerator
    @Column(name = "question_id", nullable = false, updatable = false)
    UUID questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    QuizQuestion quiz;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    String content;

    @Column(name = "question_order", nullable = false)
    Integer questionOrder;

    @Column(name = "time_limit_seconds", nullable = false)
    @Builder.Default
    Integer timeLimitSeconds = 5;

    @Column(name = "points")
    @Builder.Default
    Double points = 1.0;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<Answer> answers = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    // Helper methods
    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setQuestion(null);
    }
}