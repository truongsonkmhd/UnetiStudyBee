package com.truongsonkmhd.unetistudy.dto;

import lombok.*;

import java.time.Instant;
import java.util.*;

public class QuizDTO {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StartQuizResponse {
        private UUID attemptId;
        private UUID quizId;
        private String quizTitle;
        private Integer totalQuestions;
        private Instant startedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionResponse {
        private UUID questionId;
        private String content;
        private Integer questionOrder;
        private Integer timeLimitSeconds;
        private List<AnswerOption> answers;
        private Integer currentQuestion;
        private Integer totalQuestions;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerOption {
        private UUID answerId;
        private String content;
        private Integer answerOrder;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubmitAnswerRequest {
        private UUID questionId;
        private Set<UUID> selectedAnswerIds;
        private Integer timeSpentSeconds;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubmitAnswerResponse {
        private UUID userAnswerId;
        private Boolean isCorrect;
        private Double pointsEarned;
        private Boolean isTimeout;
        private Boolean hasNextQuestion;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuizResultResponse {
        private UUID attemptId;
        private Double score;
        private Double totalPoints;
        private Double percentage;
        private Boolean isPassed;
        private Instant startedAt;
        private Instant completedAt;
        private List<QuestionResult> questionResults;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionResult {
        private UUID questionId;
        private String questionContent;
        private Boolean isCorrect;
        private Double pointsEarned;
        private Double maxPoints;
        private Integer timeSpentSeconds;
        private Boolean isTimeout;
        private List<AnswerDetail> answers;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerDetail {
        private UUID answerId;
        private String content;
        private Boolean isCorrect;
        private Boolean isSelected;
    }
}