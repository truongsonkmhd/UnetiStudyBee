package com.truongsonkmhd.unetistudy.controller.course;

import com.truongsonkmhd.unetistudy.dto.a_common.IResponseMessage;
import com.truongsonkmhd.unetistudy.dto.a_common.ResponseMessage;
import com.truongsonkmhd.unetistudy.dto.quiz_dto.QuizAdminDTO;
import com.truongsonkmhd.unetistudy.service.QuizAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/quiz")
@RequiredArgsConstructor
public class QuizAdminController {

    private final QuizAdminService quizAdminService;

    /**
     * Create a new quiz with questions and answers
     * POST /api/admin/quiz
     */
    // @PostMapping
    // public ResponseEntity<IResponseMessage> createQuiz(
    // @Valid @RequestBody QuizAdminDTO.CreateQuizRequest request) {
    //
    // QuizAdminDTO.QuizResponse response = quizAdminService.createQuiz(request);
    // return
    // ResponseEntity.ok().body(ResponseMessage.CreatedSuccess(response));
    // }

    /**
     * Update quiz basic information
     * PUT /api/admin/quiz/{quizId}
     */
    @PutMapping("/{quizId}")
    public ResponseEntity<IResponseMessage> updateQuiz(
            @PathVariable UUID quizId,
            @Valid @RequestBody QuizAdminDTO.UpdateQuizRequest request) {

        QuizAdminDTO.QuizResponse response = quizAdminService.updateQuiz(quizId, request);
        return ResponseEntity.ok().body(ResponseMessage.UpdatedSuccess(response));
    }

    /**
     * Delete a quiz (only if not published)
     * DELETE /api/admin/quiz/{quizId}
     */
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable UUID quizId) {
        quizAdminService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get quiz details with all questions and answers
     * GET /api/admin/quiz/{quizId}
     */
    @GetMapping("/{quizId}")
    public ResponseEntity<IResponseMessage> getQuizById(@PathVariable UUID quizId) {
        QuizAdminDTO.QuizResponse response = quizAdminService.getQuizById(quizId);
        return ResponseEntity.ok().body(ResponseMessage.LoadedSuccess(response));
    }

    /**
     * Get all quizzes for a contest lesson
     * GET /api/admin/quiz?contestLessonId={id}
     */
    @GetMapping
    public ResponseEntity<IResponseMessage> getAllQuizzes(
            @RequestParam UUID contestLessonId) {

        List<QuizAdminDTO.QuizSummaryResponse> responses = quizAdminService.getAllQuizzes(contestLessonId);
        return ResponseEntity.ok().body(ResponseMessage.LoadedSuccess(responses));
    }

    /**
     * Publish a quiz (make it available to students)
     * POST /api/admin/quiz/{quizId}/publish
     */
    @PostMapping("/{quizId}/publish")
    public ResponseEntity<IResponseMessage> publishQuiz(@PathVariable UUID quizId) {
        QuizAdminDTO.UpdateQuizRequest request = QuizAdminDTO.UpdateQuizRequest.builder()
                .isPublished(true)
                .build();

        QuizAdminDTO.QuizResponse response = quizAdminService.updateQuiz(quizId, request);
        return ResponseEntity.ok().body(ResponseMessage.CreatedSuccess(response));
    }

    /**
     * Unpublish a quiz
     * POST /api/admin/quiz/{quizId}/unpublish
     */
    @PostMapping("/{quizId}/unpublish")
    public ResponseEntity<IResponseMessage> unpublishQuiz(@PathVariable UUID quizId) {
        QuizAdminDTO.UpdateQuizRequest request = QuizAdminDTO.UpdateQuizRequest.builder()
                .isPublished(false)
                .build();

        QuizAdminDTO.QuizResponse response = quizAdminService.updateQuiz(quizId, request);
        return ResponseEntity.ok().body(ResponseMessage.CreatedSuccess(response));
    }

    // ==================== QUESTION ENDPOINTS ====================

    /**
     * Add a new question to a quiz
     * POST /api/admin/quiz/question
     */
    @PostMapping("/question")
    public ResponseEntity<IResponseMessage> addQuestion(
            @Valid @RequestBody QuizAdminDTO.AddQuestionRequest request) {

        QuizAdminDTO.QuestionResponse response = quizAdminService.addQuestion(request);
        return ResponseEntity.ok().body(ResponseMessage.CreatedSuccess(response));
    }

    /**
     * Update a question
     * PUT /api/admin/quiz/question/{questionId}
     */
    @PutMapping("/question/{questionId}")
    public ResponseEntity<IResponseMessage> updateQuestion(
            @PathVariable UUID questionId,
            @Valid @RequestBody QuizAdminDTO.UpdateQuestionRequest request) {

        QuizAdminDTO.QuestionResponse response = quizAdminService.updateQuestion(questionId, request);
        return ResponseEntity.ok().body(ResponseMessage.UpdatedSuccess(response));
    }

    /**
     * Delete a question
     * DELETE /api/admin/quiz/question/{questionId}
     */
    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID questionId) {
        quizAdminService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    // ==================== ANSWER ENDPOINTS ====================

    /**
     * Add a new answer to a question
     * POST /api/admin/quiz/answer
     */
    @PostMapping("/answer")
    public ResponseEntity<IResponseMessage> addAnswer(
            @Valid @RequestBody QuizAdminDTO.AddAnswerRequest request) {

        QuizAdminDTO.AnswerResponse response = quizAdminService.addAnswer(request);
        return ResponseEntity.ok().body(ResponseMessage.CreatedSuccess(response));
    }

    /**
     * Update an answer
     * PUT /api/admin/quiz/answer/{answerId}
     */
    @PutMapping("/answer/{answerId}")
    public ResponseEntity<IResponseMessage> updateAnswer(
            @PathVariable UUID answerId,
            @Valid @RequestBody QuizAdminDTO.UpdateAnswerRequest request) {

        QuizAdminDTO.AnswerResponse response = quizAdminService.updateAnswer(answerId, request);
        return ResponseEntity.ok().body(ResponseMessage.UpdatedSuccess(response));
    }

    /**
     * Delete an answer
     * DELETE /api/admin/quiz/answer/{answerId}
     */
    @DeleteMapping("/answer/{answerId}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable UUID answerId) {
        quizAdminService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }

}