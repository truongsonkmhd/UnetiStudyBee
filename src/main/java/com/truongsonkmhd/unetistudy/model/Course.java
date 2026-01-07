package com.truongsonkmhd.unetistudy.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_course")
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

    @Column(name = "language", length = 20, nullable = false)
    String language = "vi"; // Giá trị mặc định

    @Column(name = "duration")
    Integer duration;

    @Column(name = "capacity")
    Integer capacity;

    @Column(name = "enrolledCount", nullable = false)
    Integer enrolledCount = 0;

    @Column(name = "rating", precision = 3, scale = 2, nullable = false)
    BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "ratingCount", nullable = false)
    Integer ratingCount = 0;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    BigDecimal price = BigDecimal.ZERO;

    @Column(name = "discountPrice", precision = 10, scale = 2)
    BigDecimal discountPrice;

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

    @Column(name = "publishedAt")
    LocalDateTime publishedAt;

    @Column(name = "created_at", length = 255)
    Date createdAt;

    @Column(name = "updated_at", length = 255)
    Date updatedAt;
}
