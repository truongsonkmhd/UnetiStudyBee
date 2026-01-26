package com.truongsonkmhd.unetistudy.service.impl.quiz;

import com.truongsonkmhd.unetistudy.dto.quiz_dto.QuizAdminDTO;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.ContestLesson;
import com.truongsonkmhd.unetistudy.model.quiz.Quiz;
import com.truongsonkmhd.unetistudy.model.quiz.Answer;
import com.truongsonkmhd.unetistudy.model.quiz.Question;
import com.truongsonkmhd.unetistudy.repository.course.ContestLessonRepository;
import com.truongsonkmhd.unetistudy.repository.quiz.AnswerRepository;
import com.truongsonkmhd.unetistudy.repository.quiz.QuestionRepository;
import com.truongsonkmhd.unetistudy.repository.quiz.QuizQuestionRepository;
import com.truongsonkmhd.unetistudy.service.QuizAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizAdminServiceImpl implements QuizAdminService {

    private final QuizQuestionRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ContestLessonRepository contestLessonRepository;

    // @Override
    // @Transactional
    // public QuizAdminDTO.QuizResponse createQuiz(QuizAdminDTO.CreateQuizRequest
    // request) {
    // // Validate contest lesson exists
    // ContestLesson contestLesson =
    // contestLessonRepository.findById(request.getContestLessonId())
    // .orElseThrow(() -> new RuntimeException("Contest lesson not found"));
    //
    // // Validate at least one correct answer per question
    // validateQuestions(request.getQuestions());
    //
    // // Create quiz
    // Quiz quiz = Quiz.builder()
    // .contestLesson(contestLesson)
    // .title(request.getTitle())
    // .passScore(request.getPassScore())
    // .isPublished(request.getIsPublished())
    // .totalQuestions(request.getQuestions().size())
    // .description(request.getDescription())
    // .build();
    //
    //
    // for (QuizAdminDTO.CreateQuestionRequest qReq : request.getQuestions()) {
    // Question question = Question.builder()
    // .quiz(quiz)
    // .content(qReq.getContent())
    // .questionOrder(qReq.getQuestionOrder())
    // .timeLimitSeconds(qReq.getTimeLimitSeconds())
    // .points(qReq.getPoints())
    // .build();
    //
    // for (QuizAdminDTO.CreateAnswerRequest aReq : qReq.getAnswers()) {
    // Answer answer = Answer.builder()
    // .question(question)
    // .content(aReq.getContent())
    // .answerOrder(aReq.getAnswerOrder())
    // .isCorrect(aReq.getIsCorrect())
    // .build();
    // question.addAnswer(answer);
    // }
    //
    // quiz.addQuestion(question);
    // }
    //
    //
    // quiz = quizRepository.save(quiz);
    //
    // return mapToQuizResponse(quiz, quiz.getQuestions());
    // }

    @Override
    @Transactional
    public QuizAdminDTO.QuizResponse updateQuiz(UUID quizId, QuizAdminDTO.UpdateQuizRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (request.getTitle() != null) {
            quiz.setTitle(request.getTitle());
        }
        if (request.getPassScore() != null) {
            quiz.setPassScore(request.getPassScore());
        }
        if (request.getIsPublished() != null) {
            // Validate before publishing
            if (request.getIsPublished() && !validateQuizBeforePublish(quiz)) {
                throw new RuntimeException("Quiz validation failed. Cannot publish.");
            }
            quiz.setIsPublished(request.getIsPublished());
        }

        quiz = quizRepository.save(quiz);
        List<Question> questions = questionRepository.findByQuizOrderByQuestionOrderAsc(quiz);

        return mapToQuizResponse(quiz, questions);
    }

    @Override
    @Transactional
    public void deleteQuiz(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (quiz.getIsPublished()) {
            throw new RuntimeException("Cannot delete published quiz");
        }

        quizRepository.delete(quiz);
    }

    @Override
    @Transactional(readOnly = true)
    public QuizAdminDTO.QuizResponse getQuizById(UUID quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        List<Question> questions = questionRepository.findByQuizOrderByQuestionOrderAsc(quiz);
        return mapToQuizResponse(quiz, questions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizAdminDTO.QuizSummaryResponse> getAllQuizzes(UUID contestLessonId) {
        ContestLesson contestLesson = contestLessonRepository.findById(contestLessonId)
                .orElseThrow(() -> new RuntimeException("Contest lesson not found"));

        List<Quiz> quizzes = quizRepository.findByContestLessonAndIsPublishedTrue(contestLesson);

        return quizzes.stream()
                .map(this::mapToQuizSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuizAdminDTO.QuestionResponse addQuestion(QuizAdminDTO.AddQuestionRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (quiz.getIsPublished()) {
            throw new RuntimeException("Cannot modify published quiz");
        }

        // Validate at least one correct answer
        if (request.getAnswers().stream().noneMatch(QuizAdminDTO.CreateAnswerRequest::getIsCorrect)) {
            throw new RuntimeException("Question must have at least one correct answer");
        }

        // Get next order number
        long questionCount = questionRepository.countByQuiz(quiz);
        int nextOrder = (int) questionCount + 1;

        Question question = Question.builder()
                .quiz(quiz)
                .content(request.getContent())
                .questionOrder(nextOrder)
                .timeLimitSeconds(request.getTimeLimitSeconds())
                .points(request.getPoints())
                .build();

        question = questionRepository.save(question);

        // Create answers
        for (QuizAdminDTO.CreateAnswerRequest aReq : request.getAnswers()) {
            Answer answer = Answer.builder()
                    .question(question)
                    .content(aReq.getContent())
                    .answerOrder(aReq.getAnswerOrder())
                    .isCorrect(aReq.getIsCorrect())
                    .build();
            answerRepository.save(answer);
        }

        // Update total questions count
        quiz.setTotalQuestions(quiz.getTotalQuestions() + 1);
        quizRepository.save(quiz);

        return mapToQuestionResponse(question);
    }

    @Override
    @Transactional
    public QuizAdminDTO.QuestionResponse updateQuestion(UUID questionId, QuizAdminDTO.UpdateQuestionRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        if (question.getQuiz().getIsPublished()) {
            throw new RuntimeException("Cannot modify published quiz");
        }

        if (request.getContent() != null) {
            question.setContent(request.getContent());
        }
        if (request.getQuestionOrder() != null) {
            question.setQuestionOrder(request.getQuestionOrder());
        }
        if (request.getTimeLimitSeconds() != null) {
            question.setTimeLimitSeconds(request.getTimeLimitSeconds());
        }
        if (request.getPoints() != null) {
            question.setPoints(request.getPoints());
        }

        question = questionRepository.save(question);
        return mapToQuestionResponse(question);
    }

    @Override
    @Transactional
    public void deleteQuestion(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        if (question.getQuiz().getIsPublished()) {
            throw new RuntimeException("Cannot modify published quiz");
        }

        Quiz quiz = question.getQuiz();
        questionRepository.delete(question);

        // Update total questions count
        quiz.setTotalQuestions(quiz.getTotalQuestions() - 1);
        quizRepository.save(quiz);

        // Reorder remaining questions
        List<Question> remainingQuestions = questionRepository.findByQuizOrderByQuestionOrderAsc(quiz);
        for (int i = 0; i < remainingQuestions.size(); i++) {
            remainingQuestions.get(i).setQuestionOrder(i + 1);
        }
        questionRepository.saveAll(remainingQuestions);
    }

    @Override
    @Transactional
    public QuizAdminDTO.AnswerResponse addAnswer(QuizAdminDTO.AddAnswerRequest request) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        if (question.getQuiz().getIsPublished()) {
            throw new RuntimeException("Cannot modify published quiz");
        }

        // Get next order number
        List<Answer> existingAnswers = answerRepository.findByQuestionOrderByAnswerOrderAsc(question);
        int nextOrder = existingAnswers.size() + 1;

        Answer answer = Answer.builder()
                .question(question)
                .content(request.getContent())
                .answerOrder(nextOrder)
                .isCorrect(request.getIsCorrect())
                .build();

        answer = answerRepository.save(answer);
        return mapToAnswerResponse(answer);
    }

    @Override
    @Transactional
    public QuizAdminDTO.AnswerResponse updateAnswer(UUID answerId, QuizAdminDTO.UpdateAnswerRequest request) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        if (answer.getQuestion().getQuiz().getIsPublished()) {
            throw new RuntimeException("Cannot modify published quiz");
        }

        if (request.getContent() != null) {
            answer.setContent(request.getContent());
        }
        if (request.getAnswerOrder() != null) {
            answer.setAnswerOrder(request.getAnswerOrder());
        }
        if (request.getIsCorrect() != null) {
            answer.setIsCorrect(request.getIsCorrect());
        }

        answer = answerRepository.save(answer);

        // Validate at least one correct answer remains
        List<Answer> answers = answerRepository.findByQuestionOrderByAnswerOrderAsc(answer.getQuestion());
        if (answers.stream().noneMatch(Answer::getIsCorrect)) {
            throw new RuntimeException("Question must have at least one correct answer");
        }

        return mapToAnswerResponse(answer);
    }

    @Override
    @Transactional
    public void deleteAnswer(UUID answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        if (answer.getQuestion().getQuiz().getIsPublished()) {
            throw new RuntimeException("Cannot modify published quiz");
        }

        Question question = answer.getQuestion();
        List<Answer> existingAnswers = answerRepository.findByQuestionOrderByAnswerOrderAsc(question);

        if (existingAnswers.size() <= 2) {
            throw new RuntimeException("Question must have at least 2 answers");
        }

        answerRepository.delete(answer);

        // Reorder remaining answers
        List<Answer> remainingAnswers = answerRepository.findByQuestionOrderByAnswerOrderAsc(question);
        for (int i = 0; i < remainingAnswers.size(); i++) {
            remainingAnswers.get(i).setAnswerOrder(i + 1);
        }
        answerRepository.saveAll(remainingAnswers);
    }

    // Helper methods
    private void validateQuestions(List<QuizAdminDTO.CreateQuestionRequest> questions) {
        for (QuizAdminDTO.CreateQuestionRequest question : questions) {
            if (question.getAnswers().stream().noneMatch(QuizAdminDTO.CreateAnswerRequest::getIsCorrect)) {
                throw new RuntimeException("Each question must have at least one correct answer");
            }
        }
    }

    private boolean validateQuizBeforePublish(Quiz quiz) {
        List<Question> questions = questionRepository.findByQuizOrderByQuestionOrderAsc(quiz);

        if (questions.isEmpty()) {
            return false;
        }

        for (Question question : questions) {
            List<Answer> answers = answerRepository.findByQuestionOrderByAnswerOrderAsc(question);
            if (answers.size() < 2) {
                return false;
            }
            if (answers.stream().noneMatch(Answer::getIsCorrect)) {
                return false;
            }
        }

        return true;
    }

    private QuizAdminDTO.QuizResponse mapToQuizResponse(Quiz quiz, List<Question> questions) {
        return QuizAdminDTO.QuizResponse.builder()
                .quizId(quiz.getId())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .totalQuestions(quiz.getTotalQuestions())
                .passScore(quiz.getPassScore())
                .isPublished(quiz.getIsPublished())
                .contestLessonId(quiz.getContestLesson().getContestLessonId())
                .createdAt(quiz.getCreatedAt())
                .updatedAt(quiz.getUpdatedAt())
                .questions(questions.stream()
                        .map(this::mapToQuestionResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private QuizAdminDTO.QuestionResponse mapToQuestionResponse(Question question) {
        List<Answer> answers = answerRepository.findByQuestionOrderByAnswerOrderAsc(question);

        return QuizAdminDTO.QuestionResponse.builder()
                .questionId(question.getId())
                .content(question.getContent())
                .questionOrder(question.getQuestionOrder())
                .timeLimitSeconds(question.getTimeLimitSeconds())
                .points(question.getPoints())
                .answers(answers.stream()
                        .map(this::mapToAnswerResponse)
                        .collect(Collectors.toList()))
                .build();
    }

    private QuizAdminDTO.AnswerResponse mapToAnswerResponse(Answer answer) {
        return QuizAdminDTO.AnswerResponse.builder()
                .answerId(answer.getId())
                .content(answer.getContent())
                .answerOrder(answer.getAnswerOrder())
                .isCorrect(answer.getIsCorrect())
                .build();
    }

    private QuizAdminDTO.QuizSummaryResponse mapToQuizSummaryResponse(Quiz quiz) {
        return QuizAdminDTO.QuizSummaryResponse.builder()
                .quizId(quiz.getId())
                .title(quiz.getTitle())
                .totalQuestions(quiz.getTotalQuestions())
                .passScore(quiz.getPassScore())
                .isPublished(quiz.getIsPublished())
                .createdAt(quiz.getCreatedAt())
                .updatedAt(quiz.getUpdatedAt())
                .build();
    }
}