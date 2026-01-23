package com.truongsonkmhd.unetistudy.sevice.impl.quiz;


import com.truongsonkmhd.unetistudy.common.AttemptStatus;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.Quiz;
import com.truongsonkmhd.unetistudy.model.quiz.Answer;
import com.truongsonkmhd.unetistudy.model.quiz.Question;
import com.truongsonkmhd.unetistudy.model.quiz.UserAnswer;
import com.truongsonkmhd.unetistudy.model.quiz.UserQuizAttempt;
import com.truongsonkmhd.unetistudy.repository.quiz.*;
import com.truongsonkmhd.unetistudy.sevice.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizQuestionRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserQuizAttemptRepository attemptRepository;
    private final UserAnswerRepository userAnswerRepository;

    @Override
    @Transactional
    public UserQuizAttempt startQuizAttempt(UUID userId, UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (!quiz.getIsPublished()) {
            throw new RuntimeException("Quiz is not published");
        }

        UserQuizAttempt attempt = UserQuizAttempt.builder()
                .userId(userId)
                .quiz(quiz)
                .startedAt(Instant.now())
                .status(AttemptStatus.IN_PROGRESS)
                .build();

        return attemptRepository.save(attempt);
    }
    @Override
    @Transactional(readOnly = true)
    public Question getNextQuestion(UUID attemptId) {
        UserQuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        // Lấy danh sách câu hỏi đã trả lời
        Set<UUID> answeredQuestionIds = attempt.getUserAnswers().stream()
                .map(ua -> ua.getQuestion().getQuestionId())
                .collect(Collectors.toSet());

        // Lấy câu hỏi tiếp theo chưa trả lời
        List<Question> questions = questionRepository
                .findByQuizOrderByQuestionOrderAsc(attempt.getQuiz());

        return questions.stream()
                .filter(q -> !answeredQuestionIds.contains(q.getQuestionId()))
                .findFirst()
                .orElse(null);
    }
    @Override
    @Transactional
    public UserAnswer submitAnswer(UUID attemptId, UUID questionId,
                                   Set<UUID> selectedAnswerIds,
                                   Integer timeSpentSeconds) {
        UserQuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Kiểm tra timeout
        boolean isTimeout = timeSpentSeconds > question.getTimeLimitSeconds();

        // Lấy các đáp án được chọn
        Set<Answer> selectedAnswers = new HashSet<>();
        if (selectedAnswerIds != null && !selectedAnswerIds.isEmpty()) {
            selectedAnswers = new HashSet<>(answerRepository.findAllById(selectedAnswerIds));
        }

        // Kiểm tra đúng sai
        Set<UUID> correctAnswerIds = question.getAnswers().stream()
                .filter(Answer::getIsCorrect)
                .map(Answer::getAnswerId)
                .collect(Collectors.toSet());

        boolean isCorrect = !isTimeout &&
                correctAnswerIds.equals(selectedAnswerIds);

        double pointsEarned = isCorrect ? question.getPoints() : 0.0;

        // Tạo UserAnswer
        UserAnswer userAnswer = UserAnswer.builder()
                .attempt(attempt)
                .question(question)
                .selectedAnswers(selectedAnswers)
                .isCorrect(isCorrect)
                .pointsEarned(pointsEarned)
                .timeSpentSeconds(timeSpentSeconds)
                .isTimeout(isTimeout)
                .build();

        attempt.addUserAnswer(userAnswer);
        return userAnswerRepository.save(userAnswer);
    }
    @Override
    @Transactional
    public UserQuizAttempt completeQuizAttempt(UUID attemptId) {
        UserQuizAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        // Tính điểm
        double totalScore = attempt.getUserAnswers().stream()
                .mapToDouble(UserAnswer::getPointsEarned)
                .sum();

        double totalPossiblePoints = questionRepository
                .findByQuizOrderByQuestionOrderAsc(attempt.getQuiz())
                .stream()
                .mapToDouble(Question::getPoints)
                .sum();

        double percentage = totalPossiblePoints > 0
                ? (totalScore / totalPossiblePoints) * 100
                : 0.0;

        boolean isPassed = attempt.getQuiz().getPassScore() != null
                && percentage >= attempt.getQuiz().getPassScore();

        attempt.setScore(totalScore);
        attempt.setTotalPoints(totalPossiblePoints);
        attempt.setPercentage(percentage);
        attempt.setIsPassed(isPassed);
        attempt.setCompletedAt(Instant.now());
        attempt.setStatus(AttemptStatus.COMPLETED);

        return attemptRepository.save(attempt);
    }
    @Override
    @Transactional(readOnly = true)
    public List<UserQuizAttempt> getUserAttempts(UUID userId, UUID quizId) {
        return attemptRepository.findByUserIdAndQuizOrderByCreatedAtDesc(userId, quizId);
    }
}