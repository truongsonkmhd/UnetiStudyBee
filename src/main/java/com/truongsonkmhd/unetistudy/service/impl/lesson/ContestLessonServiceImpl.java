package com.truongsonkmhd.unetistudy.service.impl.lesson;

import com.truongsonkmhd.unetistudy.cache.CacheConstants;
import com.truongsonkmhd.unetistudy.common.StatusContest;
import com.truongsonkmhd.unetistudy.dto.a_common.PageResponse;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonRequestDTO;
import com.truongsonkmhd.unetistudy.dto.contest_lesson.ContestLessonResponseDTO;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.CodingExercise;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.ContestLesson;
import com.truongsonkmhd.unetistudy.model.lesson.course_lesson.ExerciseTestCase;
import com.truongsonkmhd.unetistudy.model.coding_template.CodingExerciseTemplate;
import com.truongsonkmhd.unetistudy.model.quiz.Quiz;
import com.truongsonkmhd.unetistudy.model.quiz.template.QuizTemplate;
import com.truongsonkmhd.unetistudy.repository.coding.CodingExerciseTemplateRepository;
import com.truongsonkmhd.unetistudy.repository.course.ContestLessonRepository;
import com.truongsonkmhd.unetistudy.repository.quiz.QuizTemplateRepository;
import com.truongsonkmhd.unetistudy.service.ContestLessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service quản lý Contest Lesson với tích hợp Caching
 * 
 * Cache Patterns áp dụng:
 * 1. Cache-Aside - @Cacheable cho searchContestLessons
 * 2. Cache Invalidation - @CacheEvict khi addContestLesson
 * 3. Time-based Expiration - TTL 15 phút
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContestLessonServiceImpl implements ContestLessonService {

    private final ContestLessonRepository contestLessonRepository;
    private final CodingExerciseTemplateRepository templateRepository;
    private final QuizTemplateRepository quizTemplateRepository;

    /**
     * Cache Invalidation: Xóa cache danh sách contest khi tạo mới
     */
    @Override
    @CacheEvict(cacheNames = CacheConstants.CONTESTS, allEntries = true)
    public ContestLessonResponseDTO addContestLesson(ContestLessonRequestDTO request) {
        log.info("Adding new contest lesson: {} - Evicting cache", request.getTitle());

        ContestLesson contestLesson = ContestLesson.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .defaultDurationMinutes(request.getDefaultDurationMinutes())
                .totalPoints(request.getTotalPoints())
                .defaultMaxAttempts(request.getDefaultMaxAttempts())
                .passingScore(request.getPassingScore())
                .showLeaderboardDefault(request.getShowLeaderboardDefault())
                .instructions(request.getInstructions())
                .status(StatusContest.DRAFT)
                .build();

        addCodingExercisesToContest(request.getExerciseTemplateIds(), contestLesson);
        addQuizToContest(request.getQuizTemplateIds(), contestLesson);

        contestLessonRepository.save(contestLesson);

        return ContestLessonResponseDTO.builder()
                .title(contestLesson.getTitle())
                .description(contestLesson.getDescription())
                .defaultDurationMinutes(contestLesson.getDefaultDurationMinutes())
                .totalPoints(contestLesson.getTotalPoints())
                .defaultMaxAttempts(contestLesson.getDefaultMaxAttempts())
                .passingScore(contestLesson.getPassingScore())
                .showLeaderboardDefault(contestLesson.getShowLeaderboardDefault())
                .instructions(contestLesson.getInstructions())
                .status(contestLesson.getStatus())
                .build();
    }

    /**
     * Cache-Aside: Tìm kiếm contest lessons
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheConstants.CONTESTS, key = "'search:' + #page + ':' + #size + ':' + (#q ?: '') + ':' + (#statusContest ?: '')", unless = "#result.items.isEmpty()")
    public PageResponse<ContestLessonResponseDTO> searchContestLessons(
            int page,
            int size,
            String q,
            StatusContest statusContest) {

        log.debug("Cache MISS - Searching contest lessons: q={}, status={}", q, statusContest);

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        Pageable pageable = PageRequest.of(safePage, safeSize);
        Page<ContestLessonResponseDTO> result = contestLessonRepository.searchContestAdvance(q, statusContest,
                pageable);

        return buildPageResponse(result);
    }

    private PageResponse<ContestLessonResponseDTO> buildPageResponse(
            Page<ContestLessonResponseDTO> page) {
        return PageResponse.<ContestLessonResponseDTO>builder()
                .items(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .build();
    }

    public void addCodingExercisesToContest(List<UUID> exerciseTemplateIds2, ContestLesson contestLesson) {

        if (exerciseTemplateIds2 != null && !exerciseTemplateIds2.isEmpty()) {
            List<CodingExerciseTemplate> templates = templateRepository
                    .findAllById(exerciseTemplateIds2);

            for (CodingExerciseTemplate template : templates) {
                CodingExercise contestExercise = template.toContestExercise();

                for (var templateTestCase : template.getTestCases()) {
                    ExerciseTestCase testCase = ExerciseTestCase.builder()
                            .input(templateTestCase.getInput())
                            .expectedOutput(templateTestCase.getExpectedOutput())
                            .isSample(templateTestCase.getIsSample())
                            .explanation(templateTestCase.getExplanation())
                            .orderIndex(templateTestCase.getOrderIndex())
                            .build();
                    contestExercise.addTestCase(testCase);
                }

                contestLesson.addCodingExercise(contestExercise);
                template.incrementUsageCount();
            }
        }
    }

    @Transactional
    public void addQuizToContest(List<UUID> quizTemplateIds, ContestLesson contestLesson) {

        if (quizTemplateIds == null || quizTemplateIds.isEmpty()) {
            return;
        }

        List<QuizTemplate> templates = quizTemplateRepository.findAllById(quizTemplateIds);

        templates.forEach(template -> {
            Quiz quiz = template.toQuiz();
            contestLesson.addQuizQuestion(quiz);
            template.incrementUsageCount();
        });
    }

}