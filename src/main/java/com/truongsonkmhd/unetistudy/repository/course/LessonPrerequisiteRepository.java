package com.truongsonkmhd.unetistudy.repository.course;

import com.truongsonkmhd.unetistudy.model.lesson.LessonPrerequisite;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LessonPrerequisiteRepository extends JpaRepository<LessonPrerequisite, UUID> {
    @Query("""
        select lp
        from LessonPrerequisite lp
        where lp.lesson.lessonId = :lessonId
    """)
    List<LessonPrerequisite> findByLessonId(@Param("lessonId") UUID lessonId);
}
