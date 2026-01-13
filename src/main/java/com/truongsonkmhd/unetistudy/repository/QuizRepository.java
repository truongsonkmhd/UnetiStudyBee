package com.truongsonkmhd.unetistudy.repository;

import com.truongsonkmhd.unetistudy.model.lesson.Quiz;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {
    @Query("""
        select avg(qa.passScore)
        from Quiz qa
        where qa.lesson.lessonId = :lessonId
    """)
    Double avgScore(
            @Param("userId") UUID userId,
            @Param("lessonId") UUID lessonId
    );
}
