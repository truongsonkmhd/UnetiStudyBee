package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseCardResponse;
import com.truongsonkmhd.unetistudy.model.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    //Hàm này tương đương: SELECT COUNT(*) > 0 FROM courses WHERE slug = :slug
    boolean existsBySlug(String slug);


    @EntityGraph(attributePaths = {
            "modules",
            "modules.lessons",
            "modules.lessons.codingExercises",
            "modules.lessons.quizzes"
    })
    Optional<Course> findBySlug(String slug);

    @Query("""
        select new com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseCardResponse(
            c.courseId, c.title, c.slug, c.shortDescription, c.imageUrl,
            c.price, c.discountPrice, c.isPublished, null, c.publishedAt
        )
        from Course c
        where c.isPublished = true
        order by c.publishedAt desc nulls last, c.createdAt desc
    """)
    Page<CourseCardResponse> findPublishedCourseCards(Pageable pageable);

    @Query("""
        select new com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseCardResponse(
            c.courseId, c.title, c.slug, c.shortDescription, c.imageUrl,
            c.price, c.discountPrice, c.isPublished, null, c.publishedAt
        )
        from Course c
        where c.isPublished = true
          and (lower(c.title) like lower(concat('%', :q, '%'))
               or lower(c.slug) like lower(concat('%', :q, '%')))
        order by c.publishedAt desc nulls last, c.createdAt desc
    """)
    Page<CourseCardResponse> searchPublishedCourseCards(String q, Pageable pageable);
}
