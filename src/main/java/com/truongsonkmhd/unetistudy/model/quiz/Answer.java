package com.truongsonkmhd.unetistudy.model.quiz;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_answer",
        indexes = {
                @Index(name = "idx_answer_question", columnList = "question_id")
        })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {

    @Id
    @UuidGenerator
    @Column(name = "answer_id", nullable = false, updatable = false)
    UUID answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    Question question;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    String content;

    @Column(name = "is_correct", nullable = false)
    @Builder.Default
    Boolean isCorrect = false;

    @Column(name = "answer_order", nullable = false)
    Integer answerOrder;
}