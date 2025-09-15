package com.truongsonkmhd.unetistudy.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class CourseLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id", nullable = false, updatable = false)
    private UUID lessonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private CourseModule module;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "content", columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Column(name = "video_url", length = 255)
    private String videoUrl;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(name = "is_preview", nullable = false)
    private Boolean isPreview = false;

    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = false;

    @Column(name = "created_at", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;

    @Column(name = "Slug", nullable = true)
    private String slug;

    @Column(name = "is_contest", nullable = true, columnDefinition = "BIT DEFAULT 0")
    private Boolean isContest;

    @Column(name = "total_points", columnDefinition = "INT DEFAULT 0")
    private Integer totalPoints;

    @Column(name = "contest_start_time")
    private Date contestStartTime;

    @Column(name = "contest_end_time")
    private Date contestEndTime;
}
