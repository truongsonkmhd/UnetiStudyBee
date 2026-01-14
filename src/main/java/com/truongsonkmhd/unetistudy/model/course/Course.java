package com.truongsonkmhd.unetistudy.model.course;

import com.truongsonkmhd.unetistudy.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "tbl_course",
        indexes = {
                @Index(name = "idx_course_instructor", columnList = "instructor_id"),
                @Index(name = "idx_course_publish", columnList = "is_published,status"),
                @Index(name = "idx_course_category", columnList = "category,subCategory"),
                @Index(name = "idx_course_created", columnList = "created_at")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "course_id")
    UUID courseId;

    @Column(name = "title", nullable = false, length = 255)
    String title;

    @Column(name = "slug", unique = true, length = 255)
    String slug;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @Column(name = "shortDescription", length = 500)
    String shortDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<CourseModule> modules = new ArrayList<>();

    @Column(name = "level", length = 20)
    String level;

    @Column(name = "category", length = 50)
    String category;

    @Column(name = "subCategory", length = 50)
    String subCategory;

    @Column(name = "duration")
    Integer duration;

    @Column(name = "capacity")
    Integer capacity;

    @Column(name = "enrolled_count", nullable = false)
    Integer enrolledCount = 0;

    @Column(name = "rating", precision = 3, scale = 2, nullable = false)
    BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "ratingCount", nullable = false)
    Integer ratingCount = 0;

    @Column(name = "imageUrl", length = 255)
    String imageUrl;

    @Column(name = "videoUrl", length = 255)
    String videoUrl;

    @Column(name = "requirements", columnDefinition = "text")
    String requirements;

    @Column(name = "objectives", columnDefinition = "text")
    String objectives;

    @Column(name = "syllabus", columnDefinition = "text")
    String syllabus;

    @Column(name = "status", length = 20, nullable = false)
    String status = "draft";

    @Column(name = "isPublished", nullable = false)
    Boolean isPublished = false;

    @Column(name = "published_at")
    LocalDateTime publishedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    Instant updatedAt;
}
