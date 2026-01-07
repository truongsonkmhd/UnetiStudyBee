package com.truongsonkmhd.unetistudy.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_course_lesson")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseLesson {

    @Id
    @UuidGenerator
    @Column(name = "lesson_id", nullable = false, updatable = false)
    UUID lessonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    CourseModule module;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(name = "title", nullable = false, length = 255)
    String title;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @Column(name = "type", nullable = false, length = 50)
    String type;

    @Column(name = "content", columnDefinition = "text")
    String content;

    @Column(name = "video_url", length = 255)
    String videoUrl;

    @Column(name = "Image", length = 255)
    String image;

    @Column(name = "duration")
    Integer duration;

    @Column(name = "order_index")
    Integer orderIndex;

    @Column(name = "is_preview", nullable = false)
    Boolean isPreview = false;

    @Column(name = "is_published", nullable = false)
    Boolean isPublished = false;

    @Column(name = "created_at", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    Date createdAt;

    @Column(name = "updated_at", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    Date updatedAt;

    @Column(name = "Slug", nullable = true)
    String slug;

    @Column(columnDefinition = "boolean default false", nullable = false)
    Boolean isContest;

    @Column(name = "total_points", columnDefinition = "INT DEFAULT 0")
    Integer totalPoints;

    @Column(name = "contest_start_time")
    Date contestStartTime;

    @Column(name = "contest_end_time")
    Date contestEndTime;
}
