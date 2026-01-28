package com.truongsonkmhd.unetistudy.cache.service;

import com.truongsonkmhd.unetistudy.cache.AppCacheService;
import com.truongsonkmhd.unetistudy.cache.CacheConstants;
import com.truongsonkmhd.unetistudy.cache.strategy.CacheStrategy;
import com.truongsonkmhd.unetistudy.dto.quiz_dto.QuizAdminDTO;
import com.truongsonkmhd.unetistudy.model.quiz.Question;
import com.truongsonkmhd.unetistudy.model.quiz.Quiz;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Service quản lý cache cho Quiz
 * Áp dụng Cache-Aside, Write-Through và Cache Invalidation
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class QuizCacheService {

    private final AppCacheService appCacheService;

    // ========================
    // QUIZ CACHING
    // ========================

    /**
     * Cache-Aside: Lấy quiz by ID
     */
    public Quiz getQuizById(UUID quizId, Supplier<Quiz> loader) {
        CacheStrategy<UUID, Quiz> cache = appCacheService.getCache(CacheConstants.QUIZ_BY_ID);
        return cache.getOrLoad(quizId, loader);
    }

    /**
     * Cache-Aside: Lấy quiz response by ID
     */
    public QuizAdminDTO.QuizResponse getQuizResponseById(UUID quizId, Supplier<QuizAdminDTO.QuizResponse> loader) {
        CacheStrategy<UUID, QuizAdminDTO.QuizResponse> cache = appCacheService.getCache(CacheConstants.QUIZ_BY_ID);
        return cache.getOrLoad(quizId, loader);
    }

    /**
     * Lấy quiz từ cache (không load từ DB)
     */
    public Optional<Quiz> getCachedQuizById(UUID quizId) {
        CacheStrategy<UUID, Quiz> cache = appCacheService.getCache(CacheConstants.QUIZ_BY_ID);
        return cache.get(quizId);
    }

    /**
     * Cache-Aside: Lấy danh sách quiz by contestLessonId
     */
    @SuppressWarnings("unchecked")
    public List<QuizAdminDTO.QuizSummaryResponse> getQuizzesByContestLesson(
            UUID contestLessonId,
            Supplier<List<QuizAdminDTO.QuizSummaryResponse>> loader) {
        CacheStrategy<UUID, List<QuizAdminDTO.QuizSummaryResponse>> cache = appCacheService
                .getCache(CacheConstants.QUIZ_LIST);
        return cache.getOrLoad(contestLessonId, loader);
    }

    /**
     * Write-Through: Cập nhật quiz và cache đồng thời
     */
    public Quiz updateQuizWithCache(UUID quizId, Quiz quiz,
            java.util.function.Function<Quiz, Quiz> dbWriter) {
        CacheStrategy<UUID, Quiz> cache = appCacheService.getCache(CacheConstants.QUIZ_BY_ID);
        Quiz savedQuiz = cache.writeThrough(quizId, quiz, dbWriter);

        // Invalidate quiz list cache cho contest lesson
        if (savedQuiz != null && savedQuiz.getContestLesson() != null) {
            invalidateQuizList(savedQuiz.getContestLesson().getContestLessonId());
        }

        return savedQuiz;
    }

    /**
     * Put quiz vào cache
     */
    public void cacheQuiz(Quiz quiz) {
        if (quiz == null)
            return;

        CacheStrategy<UUID, Quiz> cache = appCacheService.getCache(CacheConstants.QUIZ_BY_ID);
        cache.put(quiz.getId(), quiz);
    }

    /**
     * Put quiz response vào cache
     */
    public void cacheQuizResponse(UUID quizId, QuizAdminDTO.QuizResponse response) {
        CacheStrategy<UUID, QuizAdminDTO.QuizResponse> cache = appCacheService.getCache(CacheConstants.QUIZ_BY_ID);
        cache.put(quizId, response);
    }

    /**
     * Evict quiz từ cache
     */
    public void evictQuiz(UUID quizId) {
        CacheStrategy<UUID, ?> cache = appCacheService.getCache(CacheConstants.QUIZ_BY_ID);
        cache.evict(quizId);

        // Cũng evict questions của quiz này
        evictQuizQuestions(quizId);

        log.info("Evicted quiz from cache: {}", quizId);
    }

    /**
     * Evict quiz và invalidate related lists
     */
    public void evictQuizAndRelated(UUID quizId, UUID contestLessonId) {
        evictQuiz(quizId);
        if (contestLessonId != null) {
            invalidateQuizList(contestLessonId);
        }
    }

    /**
     * Invalidate quiz list cache
     */
    public void invalidateQuizList(UUID contestLessonId) {
        CacheStrategy<UUID, ?> cache = appCacheService.getCache(CacheConstants.QUIZ_LIST);
        cache.evict(contestLessonId);
        log.debug("Invalidated quiz list for contest lesson: {}", contestLessonId);
    }

    // ========================
    // QUESTION CACHING
    // ========================

    /**
     * Cache-Aside: Lấy questions by quizId
     */
    @SuppressWarnings("unchecked")
    public List<Question> getQuestionsByQuiz(UUID quizId, Supplier<List<Question>> loader) {
        CacheStrategy<UUID, List<Question>> cache = appCacheService.getCache(CacheConstants.QUIZ_QUESTIONS);
        return cache.getOrLoad(quizId, loader);
    }

    /**
     * Cache questions
     */
    public void cacheQuestions(UUID quizId, List<Question> questions) {
        CacheStrategy<UUID, List<Question>> cache = appCacheService.getCache(CacheConstants.QUIZ_QUESTIONS);
        cache.put(quizId, questions);
    }

    /**
     * Evict questions của một quiz
     */
    public void evictQuizQuestions(UUID quizId) {
        CacheStrategy<UUID, ?> cache = appCacheService.getCache(CacheConstants.QUIZ_QUESTIONS);
        cache.evict(quizId);
    }

    // ========================
    // BATCH OPERATIONS
    // ========================

    /**
     * Evict tất cả quiz caches
     */
    public void evictAllQuizzes() {
        CacheStrategy<?, ?> quizCache = appCacheService.getCache(CacheConstants.QUIZ_BY_ID);
        CacheStrategy<?, ?> listCache = appCacheService.getCache(CacheConstants.QUIZ_LIST);
        CacheStrategy<?, ?> questionCache = appCacheService.getCache(CacheConstants.QUIZ_QUESTIONS);

        quizCache.evictAll();
        listCache.evictAll();
        questionCache.evictAll();

        log.info("Evicted all quizzes from cache");
    }

    /**
     * Warm up quiz cache
     */
    public void warmUpCache(Iterable<Quiz> quizzes) {
        CacheStrategy<UUID, Quiz> cache = appCacheService.getCache(CacheConstants.QUIZ_BY_ID);

        int count = 0;
        for (Quiz quiz : quizzes) {
            cache.put(quiz.getId(), quiz);
            count++;
        }

        log.info("Warmed up quiz cache with {} quizzes", count);
    }

    /**
     * Refresh quiz trong cache
     */
    public void refreshQuiz(UUID quizId, Supplier<Quiz> loader) {
        CacheStrategy<UUID, Quiz> cache = appCacheService.getCache(CacheConstants.QUIZ_BY_ID);
        cache.refresh(quizId, loader);

        // Cũng refresh questions
        evictQuizQuestions(quizId);
    }
}
