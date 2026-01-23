package com.truongsonkmhd.unetistudy.repository.quiz;

import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.ContestLesson;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizQuestionRepository extends JpaRepository<Quiz, UUID> {
    List<Quiz> findByContestLessonAndIsPublishedTrue(ContestLesson contestLesson);
}
