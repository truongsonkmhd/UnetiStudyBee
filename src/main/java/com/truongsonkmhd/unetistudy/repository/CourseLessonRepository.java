package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.model.lesson.CourseLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CourseLessonRepository extends JpaRepository<CourseLesson, UUID> {
    @Query("""
        select l
        from CourseLesson l
        where l.module.course.courseId = :courseId
    """)
    List<CourseLesson> findLessonsByCourseId(UUID courseId);

    @Query("""
        SELECT cl
        FROM CourseLesson cl
        WHERE cl.module.moduleId = :moduleID AND cl.isContest = false
    """)
    List<CourseLesson> getLessonByModuleId(@Param("moduleID") UUID moduleID);

    @Query("""
        SELECT cl
        FROM CourseLesson cl
        WHERE cl.module.moduleId = :moduleID AND cl.slug LIKE %:slug% AND cl.isContest = false
    """)
    List<CourseLesson> getLessonByModuleIdAndSlug(@Param("moduleID") UUID moduleID,@Param("slug")String slug);

    @Query("""
        SELECT cl
        FROM CourseLesson cl
        WHERE cl.module.moduleId = :moduleID AND cl.isContest = true AND CURRENT_TIMESTAMP < cl.contestEndTime AND cl.lessonType = "CODE"
    """)
    List<CourseLesson> getCodingContest(@Param("moduleID") UUID moduleID);

    @Query("""
        SELECT cl
        FROM CourseLesson cl
        WHERE cl.module.moduleId = :moduleID AND cl.isContest = true AND CURRENT_TIMESTAMP < cl.contestEndTime AND cl.lessonType = "CODE"
    """)
    List<CourseLesson> getMultipleChoiceContest(@Param("moduleID") UUID moduleID);

    boolean existsBySlug(String slug);
}
