package com.truongsonkmhd.unetistudy.repository.course;

import com.truongsonkmhd.unetistudy.model.lesson.LessonProgress;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, UUID> {
    @Query("""
        select lp
        from LessonProgress lp
        where lp.user.id = :userId
          and lp.course.courseId = :courseId
    """)
    List<LessonProgress> findByUserAndCourse(
            @Param("userId") UUID userId,
            @Param("courseId") UUID courseId
    );

}
