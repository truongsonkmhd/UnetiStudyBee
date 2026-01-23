package com.truongsonkmhd.unetistudy.repository.quiz;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.Quiz;
import com.truongsonkmhd.unetistudy.model.quiz.UserQuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserQuizAttemptRepository extends JpaRepository<UserQuizAttempt, UUID> {
    @Query("SELECT a FROM UserQuizAttempt a WHERE a.userId = :userId AND a.quiz.quizId = :quizId ORDER BY a.createdAt DESC")
    List<UserQuizAttempt> findByUserIdAndQuizOrderByCreatedAtDesc(UUID userId, UUID quizId);

    @Query("SELECT a FROM UserQuizAttempt a WHERE a.userId = :userId AND a.quiz = :quiz AND a.status = 'IN_PROGRESS'")
    List<UserQuizAttempt> findActiveAttemptsByUserAndQuiz(UUID userId, Quiz quiz);
}
