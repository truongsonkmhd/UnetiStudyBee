package com.truongsonkmhd.unetistudy.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "course_id")
    private UUID course_id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;
    @Column(name = "slug", unique = true, length = 255)
    private String slug;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(name = "shortDescription", length = 500)
    private String shortDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CourseModule> modules = new ArrayList<>();

    @Column(name = "level", length = 20)
    private String level;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "subCategory", length = 50)
    private String subCategory;

    @Column(name = "language", length = 20, nullable = false)
    private String language = "vi"; // Giá trị mặc định

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "enrolledCount", nullable = false)
    private Integer enrolledCount = 0;

    @Column(name = "rating", precision = 3, scale = 2, nullable = false)
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(name = "ratingCount", nullable = false)
    private Integer ratingCount = 0;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "discountPrice", precision = 10, scale = 2)
    private BigDecimal discountPrice;

    @Column(name = "imageUrl", length = 255)
    private String imageUrl;

    @Column(name = "videoUrl", length = 255)
    private String videoUrl;

    @Column(name = "requirements", columnDefinition = "NVARCHAR(MAX)")
    private String requirements;

    @Column(name = "objectives", columnDefinition = "NVARCHAR(MAX)")
    private String objectives;

    @Column(name = "syllabus", columnDefinition = "NVARCHAR(MAX)")
    private String syllabus;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "draft";

    @Column(name = "isPublished", nullable = false)
    private Boolean isPublished = false;

    @Column(name = "publishedAt")
    private LocalDateTime publishedAt;

    @Column(name = "created_at", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;
}
