package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.common.CourseStatus;
import com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseCardResponse;
import com.truongsonkmhd.unetistudy.model.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    List<Course> findByStatus(CourseStatus status);

    //Hàm này tương đương: SELECT COUNT(*) > 0 FROM courses WHERE slug = :slug
    boolean existsBySlug(String slug);

    Optional<Course> findBySlug(String slug);

    @Query(
            value = """
        select new com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseCardResponse(
            c.courseId, c.title, c.slug, c.shortDescription, c.imageUrl, c.isPublished, size(c.modules) ,c.publishedAt
        )
        from Course c
        where c.isPublished = true
        order by
            case when c.publishedAt is null then 1 else 0 end,
            c.publishedAt desc,
            c.createdAt desc
    """,
            countQuery = """
        select count(c)
        from Course c
        where c.isPublished = true
    """
    )
    Page<CourseCardResponse> findPublishedCourseCards(Pageable pageable);

    @Query(
            value = """
        select new com.truongsonkmhd.unetistudy.dto.CourseDTO.CourseCardResponse(
            c.courseId, c.title, c.slug, c.shortDescription, c.imageUrl, c.isPublished,  size(c.modules), c.publishedAt
        )
        from Course c
        where c.isPublished = true
          and (
               lower(c.title) like lower(concat('%', :q, '%'))
            or lower(c.slug)  like lower(concat('%', :q, '%'))
          )
        order by
            case when c.publishedAt is null then 1 else 0 end,
            c.publishedAt desc,
            c.createdAt desc
    """,
            countQuery = """
        select count(c)
        from Course c
        where c.isPublished = true
          and (
               lower(c.title) like lower(concat('%', :q, '%'))
            or lower(c.slug)  like lower(concat('%', :q, '%'))
          )
    """
    )
    Page<CourseCardResponse> searchPublishedCourseCards(String q, Pageable pageable);

}
