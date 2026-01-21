package com.truongsonkmhd.unetistudy.repository.course;

import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.QuizQuestion;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface QuizRepository extends JpaRepository<QuizQuestion, UUID> {

    @Query("""
        select q
        from QuizQuestion q
        where q.contestLesson.courseLesson.lessonId in :lessonIds
        """)
    List<QuizQuestion> findQuizzesByLessonIds(List<UUID> lessonIds);

    @Query("""
        select avg(qa.passScore)
        from QuizQuestion qa
        where qa.contestLesson.courseLesson.lessonId= :lessonId
    """)
    Double avgScore(
            @Param("userId") UUID userId,
            @Param("lessonId") UUID lessonId
    );
}
