package com.truongsonkmhd.unetistudy.model.lesson;

import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.CourseLesson;
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
        name = "tbl_lesson_skill_tag",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_lesson_tag",
                columnNames = {"lesson_id", "tag"}
        ),
        indexes = {
                @Index(name = "idx_tag_lesson", columnList = "lesson_id"),
                @Index(name = "idx_tag_tag", columnList = "tag")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonSkillTag {

    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    CourseLesson lesson;

    @Column(name = "tag", nullable = false, length = 100)
    String tag; // "http", "oop", "sql", "client-server", ...
}