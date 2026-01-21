package com.truongsonkmhd.unetistudy.model.lesson.solid.course_lesson;

import com.truongsonkmhd.unetistudy.common.LessonType;
import com.truongsonkmhd.unetistudy.model.User;
import com.truongsonkmhd.unetistudy.model.course.CourseModule;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_course_lesson",
        indexes = {
                @Index(name = "idx_lesson_module", columnList = "module_id"),
                @Index(name = "idx_lesson_slug", columnList = "slug"),
                @Index(name = "idx_lesson_published", columnList = "is_published")
        })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseLesson {

    @Id
    @UuidGenerator
    @Column(name = "lesson_id", nullable = false, updatable = false)
    UUID lessonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    CourseModule module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    User creator;

    // Relationship with ContestLesson (one-to-one, optional)
    @OneToOne(mappedBy = "courseLesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    ContestLesson contestLesson;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @Column(name = "content", columnDefinition = "text")
    String content;

    @Column(name = "video_url")
    String videoUrl;

    @Column(name = "duration")
    Integer duration;

    @Column(name = "order_index", nullable = false)
    @Builder.Default
    Integer orderIndex = 0;

    @Column(name = "is_preview", nullable = false)
    @Builder.Default
    Boolean isPreview = false;

    @Column(name = "is_published", nullable = false)
    @Builder.Default
    Boolean isPublished = false;

    @Column(name = "slug", unique = true, nullable = false)
    String slug;

    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_type", nullable = false, length = 30)
    LessonType lessonType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Instant updatedAt;

    // Helper methods
    public void setContestLesson(ContestLesson contestLesson) {
        if (contestLesson == null) {
            if (this.contestLesson != null) {
                this.contestLesson.setCourseLesson(null);
            }
        } else {
            contestLesson.setCourseLesson(this);
        }
        this.contestLesson = contestLesson;
    }
}