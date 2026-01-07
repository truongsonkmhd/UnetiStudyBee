package com.truongsonkmhd.unetistudy.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

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
@Table(name = "tbl_course_module")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseModule {

    @Id
    @UuidGenerator
    @Column(name = "module_id", nullable = false, updatable = false)
    UUID moduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    Course course;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<CourseLesson> lessons = new ArrayList<>();

    @Column(name = "title", nullable = false, length = 255)
    String title;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @Column(name = "order_index", nullable = false)
    Integer orderIndex;

    @Column(name = "duration")
    Integer duration;

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

    @Column(name = "slug", nullable = true)
    private String slug;

}
