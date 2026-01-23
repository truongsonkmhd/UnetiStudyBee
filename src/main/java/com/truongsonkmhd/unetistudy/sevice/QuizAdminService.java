package com.truongsonkmhd.unetistudy.sevice;

import com.truongsonkmhd.unetistudy.dto.quiz_dto.QuizAdminDTO;

import java.util.List;
import java.util.UUID;

public interface QuizAdminService {
    QuizAdminDTO.QuizResponse createQuiz(QuizAdminDTO.CreateQuizRequest request);

    QuizAdminDTO.QuizResponse updateQuiz(UUID quizId, QuizAdminDTO.UpdateQuizRequest request);

    void deleteQuiz(UUID quizId);

    QuizAdminDTO.QuizResponse getQuizById(UUID quizId);

    List<QuizAdminDTO.QuizSummaryResponse> getAllQuizzes(UUID contestLessonId);

    QuizAdminDTO.QuestionResponse addQuestion(QuizAdminDTO.AddQuestionRequest request);

    QuizAdminDTO.QuestionResponse updateQuestion(UUID questionId, QuizAdminDTO.UpdateQuestionRequest request);

    void deleteQuestion(UUID questionId);

    QuizAdminDTO.AnswerResponse addAnswer(QuizAdminDTO.AddAnswerRequest request);

    QuizAdminDTO.AnswerResponse updateAnswer(UUID answerId, QuizAdminDTO.UpdateAnswerRequest request);

    void deleteAnswer(UUID answerId);
}
