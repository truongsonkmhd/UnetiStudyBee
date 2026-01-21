package com.truongsonkmhd.unetistudy.repository.quiz;

import com.truongsonkmhd.unetistudy.model.quiz.UserAnswer;
import com.truongsonkmhd.unetistudy.model.quiz.UserQuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, UUID> {
    List<UserAnswer> findByAttempt(UserQuizAttempt attempt);
}