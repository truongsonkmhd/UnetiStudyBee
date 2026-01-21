package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.model.quiz.Question;
import com.truongsonkmhd.unetistudy.model.quiz.UserAnswer;
import com.truongsonkmhd.unetistudy.model.quiz.UserQuizAttempt;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface QuizService{
    UserQuizAttempt startQuizAttempt(UUID userId, UUID quizId);

    Question getNextQuestion(UUID attemptId);

    UserAnswer submitAnswer(UUID attemptId, UUID questionId,
                            Set<UUID> selectedAnswerIds,
                            Integer timeSpentSeconds);

    UserQuizAttempt completeQuizAttempt(UUID attemptId);

    List<UserQuizAttempt> getUserAttempts(UUID userId, UUID quizId);
}

