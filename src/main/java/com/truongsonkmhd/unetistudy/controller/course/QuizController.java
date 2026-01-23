package com.truongsonkmhd.unetistudy.controller.course;

import com.truongsonkmhd.unetistudy.common.AttemptStatus;
import com.truongsonkmhd.unetistudy.context.UserContext;
import com.truongsonkmhd.unetistudy.dto.QuizDTO;
import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.SuccessResponseMessage;
import com.truongsonkmhd.unetistudy.model.quiz.Question;
import com.truongsonkmhd.unetistudy.model.quiz.UserAnswer;
import com.truongsonkmhd.unetistudy.model.quiz.UserQuizAttempt;
import com.truongsonkmhd.unetistudy.sevice.QuizService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quiz")
@Slf4j(topic = "QUIZ-CONTROLLER")
@Tag(name = "quiz-controller")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/{quizId}/start")
    public ResponseEntity<IResponseMessage> startQuiz(
            @PathVariable UUID quizId
            ) {
       UUID userID = UserContext.getUserID();

        UserQuizAttempt attempt = quizService.startQuizAttempt(userID,quizId);

        QuizDTO.StartQuizResponse response = QuizDTO.StartQuizResponse.builder()
                .attemptId(attempt.getAttemptId())
                .quizId(attempt.getQuiz().getQuizId())
                .quizTitle(attempt.getQuiz().getTitle())
                .totalQuestions(attempt.getQuiz().getTotalQuestions())
                .startedAt(attempt.getStartedAt())
                .build();

        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(response));
    }

    @GetMapping("/attempt/{attemptId}/next-question")
    public ResponseEntity<IResponseMessage> getNextQuestion(@PathVariable UUID attemptId){
        Question question = quizService.getNextQuestion(attemptId);

        if(question == null){
            return ResponseEntity.noContent().build();
        }

        List<QuizDTO.AnswerOption> answerOptions = question.getAnswers().stream()
                .map(answer -> QuizDTO.AnswerOption.builder()
                        .answerId(answer.getAnswerId())
                        .content(answer.getContent())
                        .answerOrder(answer.getAnswerOrder())
                        .build()
                )
                .sorted(Comparator.comparing(QuizDTO.AnswerOption::getAnswerOrder))
                .toList();

        QuizDTO.QuestionResponse response = QuizDTO.QuestionResponse.builder()
                .questionId(question.getQuestionId())
                .content(question.getContent())
                .questionOrder(question.getQuestionOrder())
                .timeLimitSeconds(question.getTimeLimitSeconds())
                .answers(answerOptions)
                .currentQuestion(question.getQuestionOrder())
                .totalQuestions(question.getQuiz().getTotalQuestions())
                .build();

        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(response));
    }


    @PostMapping("/attempt/{attemptId}/submit-answer")
    public ResponseEntity<IResponseMessage> submitAnswer(
            @PathVariable UUID attemptId,
            @RequestBody QuizDTO.SubmitAnswerRequest request) {

        UserAnswer userAnswer = quizService.submitAnswer(
                attemptId,
                request.getQuestionId(),
                request.getSelectedAnswerIds(),
                request.getTimeSpentSeconds()
        );

        Question nextQuestion = quizService.getNextQuestion(attemptId);

        QuizDTO.SubmitAnswerResponse response = QuizDTO.SubmitAnswerResponse.builder()
                .userAnswerId(userAnswer.getUserAnswerId())
                .isCorrect(userAnswer.getIsCorrect())
                .pointsEarned(userAnswer.getPointsEarned())
                .isTimeout(userAnswer.getIsTimeout())
                .hasNextQuestion(nextQuestion != null)
                .build();

        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(response));
    }

    @PostMapping("/attempt/{attemptId}/complete")
    public ResponseEntity<IResponseMessage> completeQuiz(
            @PathVariable UUID attemptId) {

        UserQuizAttempt attempt = quizService.completeQuizAttempt(attemptId);

        List<QuizDTO.QuestionResult> questionResults = attempt.getUserAnswers().stream()
                .map(userAnswer -> {
                    List<QuizDTO.AnswerDetail> answerDetails = userAnswer.getQuestion()
                            .getAnswers().stream()
                            .map(answer -> QuizDTO.AnswerDetail.builder()
                                    .answerId(answer.getAnswerId())
                                    .content(answer.getContent())
                                    .isCorrect(answer.getIsCorrect())
                                    .isSelected(userAnswer.getSelectedAnswers()
                                            .contains(answer))
                                    .build())
                            .collect(Collectors.toList());

                    return QuizDTO.QuestionResult.builder()
                            .questionId(userAnswer.getQuestion().getQuestionId())
                            .questionContent(userAnswer.getQuestion().getContent())
                            .isCorrect(userAnswer.getIsCorrect())
                            .pointsEarned(userAnswer.getPointsEarned())
                            .maxPoints(userAnswer.getQuestion().getPoints())
                            .timeSpentSeconds(userAnswer.getTimeSpentSeconds())
                            .isTimeout(userAnswer.getIsTimeout())
                            .answers(answerDetails)
                            .build();
                })
                .collect(Collectors.toList());

        QuizDTO.QuizResultResponse response = QuizDTO.QuizResultResponse.builder()
                .attemptId(attempt.getAttemptId())
                .score(attempt.getScore())
                .totalPoints(attempt.getTotalPoints())
                .percentage(attempt.getPercentage())
                .isPassed(attempt.getIsPassed())
                .startedAt(attempt.getStartedAt())
                .completedAt(attempt.getCompletedAt())
                .questionResults(questionResults)
                .build();

        return ResponseEntity.ok().body(SuccessResponseMessage.CreatedSuccess(response));
    }

    @GetMapping("/{quizId}/attempts")
    public ResponseEntity<IResponseMessage> getUserAttempts(
            @PathVariable UUID quizId) {

        UUID userId =  UserContext.getUserID();
        List<UserQuizAttempt> attempts = quizService.getUserAttempts(userId, quizId);

        List<QuizDTO.QuizResultResponse> responses = attempts.stream()
                .filter(attempt -> attempt.getStatus() == AttemptStatus.COMPLETED)
                .map(attempt -> QuizDTO.QuizResultResponse.builder()
                        .attemptId(attempt.getAttemptId())
                        .score(attempt.getScore())
                        .totalPoints(attempt.getTotalPoints())
                        .percentage(attempt.getPercentage())
                        .isPassed(attempt.getIsPassed())
                        .startedAt(attempt.getStartedAt())
                        .completedAt(attempt.getCompletedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(SuccessResponseMessage.LoadedSuccess(responses));
    }




}
