package com.truongsonkmhd.unetistudy.model.lesson;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "lesson_prerequisite",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_lesson_prereq",
                columnNames = {"lesson_id", "prerequisite_lesson_id"}
        ),
        indexes = {
                @Index(name = "idx_prereq_lesson", columnList = "lesson_id"),
                @Index(name = "idx_prereq_prerequisite", columnList = "prerequisite_lesson_id")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonPrerequisite {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    CourseLesson lesson;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prerequisite_lesson_id", nullable = false)
    CourseLesson prerequisiteLesson;
}
