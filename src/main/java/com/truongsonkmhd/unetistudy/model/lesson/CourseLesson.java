package com.truongsonkmhd.unetistudy.model.lesson;

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
import java.util.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_course_lesson",
        indexes = {
                @Index(name = "idx_lesson_module", columnList = "module_id"),
                @Index(name = "idx_lesson_creator", columnList = "creator_id"),
                @Index(name = "idx_lesson_slug", columnList = "slug")
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

    @Column(name = "title", nullable = false, length = 255)
    String title;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_type", nullable = false, length = 30)
    LessonType lessonType;

    @Column(name = "content", columnDefinition = "text")
    String content;

    @Column(name = "video_url", length = 255)
    String videoUrl;

    @Column(name = "duration")
    Integer duration;

    @Column(name = "order_index")
    Integer orderIndex;

    @Column(name = "is_preview", nullable = false)
    Boolean isPreview = false;

    @Column(name = "is_published", nullable = false)
    Boolean isPublished = false;

    // ====== BỔ SUNG: lesson chứa coding + quiz ======
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<CodingExercise> codingExercises = new ArrayList<>();

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<Quiz> quizzes = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    Instant updatedAt;

    @Column(name = "slug", unique = true, nullable = false)
    String slug;

    @Column(columnDefinition = "boolean default false", nullable = false)
    Boolean isContest = false;

    @Column(name = "total_points", columnDefinition = "INT DEFAULT 0")
    Integer totalPoints = 0;

    @Column(name = "contest_start_time")
    Date contestStartTime;

    @Column(name = "contest_end_time")
    Date contestEndTime;

}
